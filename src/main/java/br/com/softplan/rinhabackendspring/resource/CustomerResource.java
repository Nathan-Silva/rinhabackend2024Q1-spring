package br.com.softplan.rinhabackendspring.resource;

import br.com.softplan.rinhabackendspring.balance.BalanceService;
import br.com.softplan.rinhabackendspring.dto.ExtractResponse;
import br.com.softplan.rinhabackendspring.dto.TransactionRequest;
import br.com.softplan.rinhabackendspring.dto.TransactionResponse;
import br.com.softplan.rinhabackendspring.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "clientes")
public class CustomerResource {

    TransactionService transactionService;

    BalanceService balanceService;

    public CustomerResource(TransactionService transactionService, BalanceService balanceService) {
        this.transactionService = transactionService;
        this.balanceService = balanceService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("{id}/transacoes")
    public Mono<TransactionResponse> createTransaction(
            @PathVariable("id") Long id, @RequestBody TransactionRequest transactionRequest) {
        return transactionService.createTransaction(id, transactionRequest);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("{id}/extrato")
    public Mono<ExtractResponse> getExtract(@PathVariable("id") Long id) {
        return balanceService.getExtract(id);
    }
}
