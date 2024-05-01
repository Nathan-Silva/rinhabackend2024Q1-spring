package br.com.softplan.rinhabackendspring.dto;


import br.com.softplan.rinhabackendspring.transaction.TransactionType;

import java.math.BigInteger;

public record TransactionRequest(

        BigInteger valor,

        TransactionType tipo,

        String descricao
) {

}
