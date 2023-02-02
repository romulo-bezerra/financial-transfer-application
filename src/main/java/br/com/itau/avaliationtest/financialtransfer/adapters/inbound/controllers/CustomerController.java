package br.com.itau.avaliationtest.financialtransfer.adapters.inbound.controllers;

import br.com.itau.avaliationtest.financialtransfer.adapters.dtos.requests.CustomerRequest;
import br.com.itau.avaliationtest.financialtransfer.application.domain.Customer;
import br.com.itau.avaliationtest.financialtransfer.application.ports.service.CustomerServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerServicePort customerServicePort;

    public CustomerController(CustomerServicePort customerServicePort) {
        this.customerServicePort = customerServicePort;
    }

    @PostMapping
    public ResponseEntity<Customer> customerRegistration(@RequestBody @Valid CustomerRequest customer) {
        log.info("Start customer registration with account data");
        return ResponseEntity.status(HttpStatus.CREATED).body(customerServicePort.registerCustomer(customer));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> paginatedList(
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size) {
        log.info("Start search all customers");
        return ResponseEntity.ok(customerServicePort.findAll(size, page));
    }

}
