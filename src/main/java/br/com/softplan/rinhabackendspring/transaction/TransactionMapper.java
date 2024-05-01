package br.com.softplan.rinhabackendspring.transaction;


import br.com.softplan.rinhabackendspring.dto.TransactionRequest;

public class TransactionMapper {

    public static Transaction toEntity(TransactionRequest transactionRequest) {
        return Transaction.builder()
                .valor(transactionRequest.valor())
                .tipo(transactionRequest.tipo().getType())
                .descricao(transactionRequest.descricao())
                .build();
    }

}
