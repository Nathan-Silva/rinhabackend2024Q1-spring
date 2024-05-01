package br.com.softplan.rinhabackendspring.utils;

import br.com.softplan.rinhabackendspring.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidationsUtils {

    public void validateCustomer(Long id) {
        if (id < 1 || id > 5)
            throw new CustomerNotFoundException(404, "Customer Not Found");
    }
}
