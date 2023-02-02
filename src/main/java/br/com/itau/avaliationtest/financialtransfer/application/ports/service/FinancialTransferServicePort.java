package br.com.itau.avaliationtest.financialtransfer.application.ports.service;

import br.com.itau.avaliationtest.financialtransfer.adapters.dtos.requests.FinancialTransferRequest;
import br.com.itau.avaliationtest.financialtransfer.application.domain.FinancialTransfer;

import java.util.List;

public interface FinancialTransferServicePort {

    FinancialTransfer performTransfer(FinancialTransferRequest financialTransferRequest);
    List<FinancialTransfer> listPagedTransfersSortedByAccount(int size, int page, String sort, String accountNumber);

}
