package br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.repositories;

import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, Long> {


}
