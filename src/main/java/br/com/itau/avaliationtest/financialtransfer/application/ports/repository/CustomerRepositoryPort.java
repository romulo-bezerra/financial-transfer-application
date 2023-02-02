package br.com.itau.avaliationtest.financialtransfer.application.ports.repository;


import br.com.itau.avaliationtest.financialtransfer.adapters.dtos.requests.CustomerRequest;
import br.com.itau.avaliationtest.financialtransfer.application.domain.Customer;

import java.util.List;

public interface CustomerRepositoryPort {

    Customer save(CustomerRequest customerRequest);
    List<Customer> findAll(int size, int page);

}
