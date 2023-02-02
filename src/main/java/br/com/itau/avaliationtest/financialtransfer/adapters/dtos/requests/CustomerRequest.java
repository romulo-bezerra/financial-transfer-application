package br.com.itau.avaliationtest.financialtransfer.adapters.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "Customer name cannot be null")
    @NotBlank(message = "Customer name cannot be empty")
    private String name;

    @NotNull(message = "Customer account cannot be null")
    private AccountRequest account;

}
