package br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities;

import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.enums.TransferType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "financial_transfer")
@NoArgsConstructor
@AllArgsConstructor
public class FinancialTransferEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private AccountEntity sourceAccount;

    @OneToOne
    private AccountEntity destinationAccount;

    private LocalDateTime createdAt;

    @NotNull
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    private TransferType type;

    private Boolean success;

}
