package br.com.itau.avaliationtest.financialtransfer.adapters.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest implements Serializable {

    @NotNull(message = "Account number cannot be null")
    @NotBlank(message = "Account number cannot be empty")
    private String number;

    @NotNull(message = "Account balance cannot be null")
    @NotBlank(message = "Account balance cannot be empty")
    private BigDecimal balance;

}
