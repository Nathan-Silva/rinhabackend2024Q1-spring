package br.com.softplan.rinhabackendspring.transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;



public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long>, ReactiveSortingRepository<Transaction, Long> {

    Flux<Transaction> findByCustomerId(Long customerId, Pageable pageable);
}
