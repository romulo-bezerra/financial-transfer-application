package br.com.itau.avaliationtest.financialtransfer.application.services;

import br.com.itau.avaliationtest.financialtransfer.adapters.exceptions.AccountNotExistException;
import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities.AccountEntity;
import br.com.itau.avaliationtest.financialtransfer.application.domain.Customer;
import br.com.itau.avaliationtest.financialtransfer.application.ports.repository.AccountRepositoryPort;
import br.com.itau.avaliationtest.financialtransfer.application.ports.service.AccountServicePort;
import br.com.itau.avaliationtest.financialtransfer.application.utils.MessagePropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AccountServiceImpl implements AccountServicePort {

    private final AccountRepositoryPort accountRepositoryPort;
    private final ModelMapper mapper;

    public AccountServiceImpl(AccountRepositoryPort accountRepositoryPort, ModelMapper mapper) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.mapper = mapper;
    }

    @Override
    public Customer searchCustomerByAccount(String accountNumber) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        Optional<AccountEntity> optionalAccount = accountRepositoryPort.findByNumber(accountNumber);
        return mapper.map(optionalAccount.orElseThrow(() -> new AccountNotExistException(
                MessagePropertiesUtil.getMessage("financial-transfer.error.account-not-exist")
        )).getCustomer(), Customer.class);
    }

}
