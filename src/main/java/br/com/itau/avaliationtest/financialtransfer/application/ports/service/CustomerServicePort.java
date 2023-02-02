package br.com.itau.avaliationtest.financialtransfer.application.ports.service;

import br.com.itau.avaliationtest.financialtransfer.adapters.dtos.requests.CustomerRequest;
import br.com.itau.avaliationtest.financialtransfer.application.domain.Customer;

import java.util.List;

public interface CustomerServicePort {

    Customer registerCustomer(CustomerRequest customer);
    List<Customer> findAll(int size, int page);

}
