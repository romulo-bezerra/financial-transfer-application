package br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.repositories;

import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByNumber(@Param("number") String accountNumber);

}
