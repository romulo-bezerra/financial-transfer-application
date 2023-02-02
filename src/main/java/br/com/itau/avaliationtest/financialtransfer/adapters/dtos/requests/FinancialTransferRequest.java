package br.com.itau.avaliationtest.financialtransfer.adapters.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class FinancialTransferRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Source account number cannot be null for transfer")
    @NotBlank(message = "Source account number cannot be empty for transfer")
    @JsonProperty("source_account_number")
    private String sourceAccountNumber;

    @NotNull(message = "Destination account number cannot be null for transfer")
    @NotBlank(message = "Destination account number cannot be empty for transfer")
    @JsonProperty("destination_account_number")
    private String destinationAccountNumber;

    @NotNull(message = "transfer amount cannot be null")
    @NotBlank(message = "transfer amount cannot be empty")
    private BigDecimal value;

    @NotNull(message = "transfer type cannot be null")
    @NotBlank(message = "transfer type cannot be empty")
    private String type;

}
