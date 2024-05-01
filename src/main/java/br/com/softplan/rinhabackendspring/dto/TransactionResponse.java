package br.com.softplan.rinhabackendspring.dto;

import java.math.BigInteger;

public record TransactionResponse(
        BigInteger limite,

        BigInteger saldo
) {
}
