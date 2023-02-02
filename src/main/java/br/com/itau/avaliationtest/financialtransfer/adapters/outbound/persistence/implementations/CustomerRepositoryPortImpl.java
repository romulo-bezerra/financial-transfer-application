package br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.implementations;

import br.com.itau.avaliationtest.financialtransfer.adapters.dtos.requests.CustomerRequest;
import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities.AccountEntity;
import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities.CustomerEntity;
import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.repositories.AccountEntityRepository;
import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.repositories.CustomerEntityRepository;
import br.com.itau.avaliationtest.financialtransfer.application.domain.Account;
import br.com.itau.avaliationtest.financialtransfer.application.domain.Customer;
import br.com.itau.avaliationtest.financialtransfer.application.ports.repository.CustomerRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CustomerRepositoryPortImpl implements CustomerRepositoryPort {

    private final CustomerEntityRepository customerEntityRepository;
    private final AccountEntityRepository accountEntityRepository;
    private final ModelMapper mapper;

    public CustomerRepositoryPortImpl(CustomerEntityRepository customerEntityRepository, AccountEntityRepository accountEntityRepository, ModelMapper mapper) {
        this.customerEntityRepository = customerEntityRepository;
        this.accountEntityRepository = accountEntityRepository;
        this.mapper = mapper;
    }

    @Override
    public Customer save(CustomerRequest customerRequest) {
        log.info("m=save(), message=Customer registrations, payload={}", customerRequest);
        mapper.getConfiguration().setAmbiguityIgnored(true);

        var customer = Customer.builder()
                .name(customerRequest.getName())
                .createdAt(LocalDateTime.now())
                .build();

        var account = Account.builder()
                .number(customerRequest.getAccount().getNumber())
                .balance(customerRequest.getAccount().getBalance())
                .build();

        var customerEntity = mapper.map(customer, CustomerEntity.class);
        var accountEntity = mapper.map(account, AccountEntity.class);

        var persistedCustomer = customerEntityRepository.save(customerEntity);

        accountEntity.setCustomer(persistedCustomer);
        var persistedAccount = accountEntityRepository.save(accountEntity);

        persistedCustomer.addAccount(persistedAccount);
        customerEntityRepository.save(customerEntity);

        return mapper.map(persistedCustomer, Customer.class);
    }

    public List<Customer> findAll(int size, int page) {
        log.info("m=save(), message=Search for {} customers from pagination {}", size, page);
        mapper.getConfiguration().setAmbiguityIgnored(true);
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CustomerEntity> customersPage = customerEntityRepository.findAll(pageRequest);
        return customersPage.getContent()
                .stream()
                .map(customerEntity -> mapper.map(customerEntity, Customer.class))
                .collect(Collectors.toList());
    }
}
