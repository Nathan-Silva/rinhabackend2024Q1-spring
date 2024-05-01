package br.com.softplan.rinhabackendspring.balance;

import br.com.softplan.rinhabackendspring.dto.BalanceResponse;
import br.com.softplan.rinhabackendspring.dto.ExtractResponse;
import br.com.softplan.rinhabackendspring.dto.ExtractTransactionResponse;
import br.com.softplan.rinhabackendspring.transaction.TransactionRepository;
import br.com.softplan.rinhabackendspring.utils.CustomerValidationsUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class BalanceService {

    private final TransactionRepository transactionRepository;

    private final BalanceRepository balanceRepository;

    private final CustomerValidationsUtils customerValidationsUtils;

    public BalanceService(TransactionRepository transactionRepository, BalanceRepository balanceRepository, CustomerValidationsUtils customerValidationsUtils) {
        this.transactionRepository = transactionRepository;
        this.balanceRepository = balanceRepository;
        this.customerValidationsUtils = customerValidationsUtils;
    }

    public Mono<ExtractResponse> getExtract(Long customerId){
        customerValidationsUtils.validateCustomer(customerId);

        var pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        return balanceRepository.findByCustomerId(customerId)
                .cache()
                .subscribeOn(Schedulers.parallel())
                .map(balance -> new ExtractResponse(new BalanceResponse(balance.total, LocalDateTime.now(), balance.getLimite()), null))
                .map(extractResponse -> {
                    Mono<ExtractResponse> extractResponseMono = transactionRepository.findByCustomerId(customerId, pageable)
                            .cache()
                            .subscribeOn(Schedulers.parallel())
                            .map(transaction -> new ExtractTransactionResponse(transaction.getValor(), transaction.getTipo(), transaction.getDescricao(), transaction.getCreateDate()))
                            .collect(Collectors.toUnmodifiableList())
                            .map(extractTransactionResponses -> new ExtractResponse(extractResponse.saldo(), extractTransactionResponses));

                    return extractResponseMono;
                }).flatMap(extractResponseMono -> extractResponseMono);
    }
}
