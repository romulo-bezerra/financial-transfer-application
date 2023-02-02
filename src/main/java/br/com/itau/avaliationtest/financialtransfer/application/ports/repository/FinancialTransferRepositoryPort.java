package br.com.itau.avaliationtest.financialtransfer.application.ports.repository;


import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities.FinancialTransferEntity;
import br.com.itau.avaliationtest.financialtransfer.application.domain.FinancialTransfer;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FinancialTransferRepositoryPort {

    FinancialTransfer save(FinancialTransferEntity financialTransferEntity);
    List<FinancialTransfer> findAllByAccount(int size, int page, String sort, String accountNumber);

}
