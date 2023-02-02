package br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;


    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_account",
            joinColumns = {
                    @JoinColumn(
                            name = "customer_id",
                            referencedColumnName = "id"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "account_id",
                            referencedColumnName = "id"
                    )
            })
    private List<AccountEntity> accounts;

    public boolean addAccount(AccountEntity account) {
        this.accounts = new ArrayList<>();
        return this.accounts.add(account);
    }
}
