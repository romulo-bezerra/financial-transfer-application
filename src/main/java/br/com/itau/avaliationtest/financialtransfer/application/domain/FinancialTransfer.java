package br.com.itau.avaliationtest.financialtransfer.application.domain;

import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.enums.TransferType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialTransfer implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonProperty("source_account")
    private Account sourceAccount;

    @JsonProperty("destination_account")
    private Account destinationAccount;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    private BigDecimal value;

    private TransferType type;

    private Boolean success;

}
