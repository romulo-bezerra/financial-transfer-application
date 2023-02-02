package br.com.itau.avaliationtest.financialtransfer.adapters.inbound.controllers;

import br.com.itau.avaliationtest.financialtransfer.adapters.dtos.requests.FinancialTransferRequest;
import br.com.itau.avaliationtest.financialtransfer.application.domain.FinancialTransfer;
import br.com.itau.avaliationtest.financialtransfer.application.ports.service.FinancialTransferServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/operations")
public class FinancialTransferController {

    private final FinancialTransferServicePort financialTransferServicePort;

    public FinancialTransferController(FinancialTransferServicePort financialTransferServicePort) {
        this.financialTransferServicePort = financialTransferServicePort;
    }

    @PostMapping
    public ResponseEntity<FinancialTransfer> financialTransfer(@RequestBody @Valid FinancialTransferRequest financialTransferRequest) {
        log.info("Start of financial transfer");
        return ResponseEntity.ok(financialTransferServicePort.performTransfer(financialTransferRequest));
    }

    @GetMapping
    public ResponseEntity<List<FinancialTransfer>> paginatedListPerAccount(
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size,
            @RequestParam(
                    value = "sort",
                    defaultValue = "createdAt,desc") String sort,
            @RequestParam(
                    value = "accountNumber") String accountNumber) {
        log.info("Starting to search for transfers related to an account");
        return ResponseEntity.ok(financialTransferServicePort.listPagedTransfersSortedByAccount(size, page, sort, accountNumber));
    }

}
