package br.com.itau.avaliationtest.financialtransfer.application.services;

import br.com.itau.avaliationtest.financialtransfer.adapters.dtos.requests.CustomerRequest;
import br.com.itau.avaliationtest.financialtransfer.adapters.exceptions.AccountDuplicityException;
import br.com.itau.avaliationtest.financialtransfer.application.domain.Customer;
import br.com.itau.avaliationtest.financialtransfer.application.ports.repository.CustomerRepositoryPort;
import br.com.itau.avaliationtest.financialtransfer.application.ports.service.CustomerServicePort;
import br.com.itau.avaliationtest.financialtransfer.application.utils.MessagePropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerServicePort {

    private final CustomerRepositoryPort customerRepositoryPort;

    public CustomerServiceImpl(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public Customer registerCustomer(CustomerRequest customer) {
        try {
            return customerRepositoryPort.save(customer);
        } catch (DataIntegrityViolationException e) {
            log.error("m=regusterCustomer(), message=Account number already registered, impossible to duplicate, payload={}", customer);
            throw new AccountDuplicityException(MessagePropertiesUtil
                    .getMessage("customer.error.impossible-duplication"));
        }
    }

    @Override
    public List<Customer> findAll(int size, int page) {
        return customerRepositoryPort.findAll(size, page);
    }

}
