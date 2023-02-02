package br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.implementations;

import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.repositories.AccountEntityRepository;
import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities.AccountEntity;
import br.com.itau.avaliationtest.financialtransfer.application.domain.Account;
import br.com.itau.avaliationtest.financialtransfer.application.ports.repository.AccountRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
public class AccountRepositoryPortImpl implements AccountRepositoryPort {

    private final AccountEntityRepository accountEntityRepository;

    private final ModelMapper mapper;


    public AccountRepositoryPortImpl(AccountEntityRepository accountEntityRepository, ModelMapper mapper) {
        this.accountEntityRepository = accountEntityRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<AccountEntity> findByNumber(String accountNumber) {
        log.info("m=findByNumber(), message=Number {} account search", accountNumber);
        Optional<AccountEntity> accountEntityOptional = accountEntityRepository.findByNumber(accountNumber);
        return accountEntityOptional;
    }

    @Override
    public Account save(AccountEntity account) {
        log.info("m=save(), message=Account registration");
        mapper.getConfiguration().setAmbiguityIgnored(true);
        var accountEntity = accountEntityRepository.save(account);
        return mapper.map(accountEntity, Account.class);
    }
}
