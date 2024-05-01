package br.com.softplan.rinhabackendspring.balance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("balance")
public class Balance {

    @Id
    Long id;

    @Column("customer_id")
    Long customerId;

    BigInteger total;

    BigInteger limite;
}
