package br.com.itau.avaliationtest.financialtransfer.application.ports.repository;


import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities.AccountEntity;
import br.com.itau.avaliationtest.financialtransfer.application.domain.Account;

import java.util.Optional;

public interface AccountRepositoryPort {

    Optional<AccountEntity> findByNumber(String accountNumber);
    Account save(AccountEntity accountEntity);

}
