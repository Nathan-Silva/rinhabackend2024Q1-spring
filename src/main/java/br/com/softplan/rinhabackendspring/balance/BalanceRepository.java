package br.com.softplan.rinhabackendspring.balance;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BalanceRepository extends ReactiveCrudRepository<Balance, Long> {

    Mono<Balance> findByCustomerId(Long customerId);
}
