package br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.repositories;

import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities.FinancialTransferEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialTransferEntityRepository extends JpaRepository<FinancialTransferEntity, Long> {

    Page<FinancialTransferEntity> findAllBySourceAccount_NumberOrDestinationAccount_Number(String sourceAccountNumber,
                                                                                           String destinationAccountNumber,
                                                                                           Pageable pageable);

}
