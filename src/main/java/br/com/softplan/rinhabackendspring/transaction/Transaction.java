package br.com.softplan.rinhabackendspring.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("transaction")
public class Transaction {

    @Id

    Long id;

    @Column("customer_id")
    Long customerId;

    BigInteger valor;

    String tipo;

    String descricao;

    @Builder.Default
    @Column("realizada_em")
    LocalDateTime createDate = LocalDateTime.now();
}
