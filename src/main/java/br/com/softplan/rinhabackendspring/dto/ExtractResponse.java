package br.com.softplan.rinhabackendspring.dto;

import java.util.List;

public record ExtractResponse(
        BalanceResponse saldo,

        List<ExtractTransactionResponse> ultimas_transacoes
) {
}
