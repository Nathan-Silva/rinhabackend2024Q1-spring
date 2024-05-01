package br.com.softplan.rinhabackendspring.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

public record BalanceResponse(
        BigInteger total,
        LocalDateTime data_extracao,

        BigInteger limite
) {
}
