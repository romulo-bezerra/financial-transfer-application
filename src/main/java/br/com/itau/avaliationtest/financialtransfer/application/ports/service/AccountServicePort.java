package br.com.itau.avaliationtest.financialtransfer.application.ports.service;

import br.com.itau.avaliationtest.financialtransfer.application.domain.Customer;

public interface AccountServicePort {

    Customer searchCustomerByAccount(String accountNumber);

}
