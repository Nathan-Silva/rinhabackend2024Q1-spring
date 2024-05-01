package br.com.softplan.rinhabackendspring.transaction;

import br.com.softplan.rinhabackendspring.balance.Balance;
import br.com.softplan.rinhabackendspring.balance.BalanceRepository;
import br.com.softplan.rinhabackendspring.dto.TransactionRequest;
import br.com.softplan.rinhabackendspring.dto.TransactionResponse;
import br.com.softplan.rinhabackendspring.exceptions.InsufficientBalanceException;
import br.com.softplan.rinhabackendspring.exceptions.PayloadValidateException;
import br.com.softplan.rinhabackendspring.utils.CustomerValidationsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final BalanceRepository balanceRepository;

    private final CustomerValidationsUtils customerValidationsUtils;

    public TransactionService(TransactionRepository transactionRepository, BalanceRepository balanceRepository, CustomerValidationsUtils customerValidationsUtils) {
        this.transactionRepository = transactionRepository;
        this.balanceRepository = balanceRepository;
        this.customerValidationsUtils = customerValidationsUtils;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Mono<TransactionResponse> createTransaction(Long customerId, TransactionRequest transactionRequest){
        customerValidationsUtils.validateCustomer(customerId);
        validateRequest(transactionRequest);

        Transaction transaction = TransactionMapper.toEntity(transactionRequest);
        transaction.setCustomerId(customerId);
        Mono<Transaction> persistedTransaction = transactionRepository.save(transaction);
        return balanceRepository.findByCustomerId(customerId)
                .flatMap(balance -> {
                    if(isDebitTransaction(transactionRequest)){
                        return Mono.just(makeDebitTransaction(balance, transactionRequest));
                    }
                    return Mono.just(makeReceivableTransaction(balance, transactionRequest));
                })
                .flatMap(balanceRepository::save)
                .flatMap(persistedTransaction::thenReturn)
                .flatMap(balance -> Mono.just(new TransactionResponse(balance.getLimite(), balance.getTotal())));
    }

    public boolean isDebitTransaction(TransactionRequest transactionRequest) {
        return transactionRequest.tipo().getType().equalsIgnoreCase("D");
    }

    public Balance makeDebitTransaction(Balance balance, TransactionRequest transactionRequest) {
        validateInsufficientFounds(balance, transactionRequest);
        balance.setTotal(balance.getTotal().subtract(transactionRequest.valor()));
        return balance;
    }

    public Balance makeReceivableTransaction(Balance balance, TransactionRequest transactionRequest) {
        validateInsufficientFounds(balance, transactionRequest);
        balance.setTotal(transactionRequest.valor().add(balance.getTotal()));
        return balance;
    }

    public void validateInsufficientFounds(Balance balance, TransactionRequest transactionRequest) {
        var result = transactionRequest.valor().compareTo(balance.getTotal().add(balance.getLimite()));
        if (result > 0)
            throw new InsufficientBalanceException(422, "Insufficient Found");
    }

    public void validateRequest(TransactionRequest transactionRequest) {
        if (StringUtils.isBlank(transactionRequest.descricao()) || transactionRequest.descricao().length() > 10) {
            throw new PayloadValidateException(422, "Description field outside specifications");
        }

        if (!transactionRequest.tipo().getType().equalsIgnoreCase("D")
                && !transactionRequest.tipo().getType().equalsIgnoreCase("R")) {
            throw new PayloadValidateException(422, "Type field outside specifications");
        }
    }
}
