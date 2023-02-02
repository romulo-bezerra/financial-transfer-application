package br.com.itau.avaliationtest.financialtransfer.adapters.inbound.controllers;

import br.com.itau.avaliationtest.financialtransfer.application.domain.Customer;
import br.com.itau.avaliationtest.financialtransfer.application.ports.service.AccountServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountServicePort accountServicePort;

    public AccountController(AccountServicePort accountServicePort) {
        this.accountServicePort = accountServicePort;
    }

    @GetMapping("/{accountNumber}/customer")
    public ResponseEntity<Customer> customerSearch(@PathVariable("accountNumber") String accountNumber) {
        log.info("Initiating customer search by account number");
        return ResponseEntity.ok(accountServicePort.searchCustomerByAccount(accountNumber));
    }

}
