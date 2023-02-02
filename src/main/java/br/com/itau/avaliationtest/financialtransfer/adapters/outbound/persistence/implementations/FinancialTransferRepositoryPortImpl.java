package br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.implementations;

import br.com.itau.avaliationtest.financialtransfer.adapters.exceptions.BadRequestException;
import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.repositories.FinancialTransferEntityRepository;
import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities.FinancialTransferEntity;
import br.com.itau.avaliationtest.financialtransfer.application.domain.FinancialTransfer;
import br.com.itau.avaliationtest.financialtransfer.application.ports.repository.FinancialTransferRepositoryPort;
import br.com.itau.avaliationtest.financialtransfer.application.utils.MessagePropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class FinancialTransferRepositoryPortImpl implements FinancialTransferRepositoryPort {

    private final FinancialTransferEntityRepository financialTransferEntityRepository;

    private final ModelMapper mapper;


    public FinancialTransferRepositoryPortImpl(FinancialTransferEntityRepository financialTransferEntityRepository, ModelMapper mapper) {
        this.financialTransferEntityRepository = financialTransferEntityRepository;
        this.mapper = mapper;
    }

    @Override
    public FinancialTransfer save(FinancialTransferEntity financialTransferEntity) {
        log.info("m=save(), message=Financial transfer registration");
        mapper.getConfiguration().setAmbiguityIgnored(true);
        financialTransferEntity.setCreatedAt(LocalDateTime.now());
        var persistedFinancialTransfer = financialTransferEntityRepository.save(financialTransferEntity);
        return mapper.map(persistedFinancialTransfer, FinancialTransfer.class);
    }

    /**
     * Metodo de busca paginada e ordenada (por um unico campo) dinamicamente de todas
     * as transferencias relaconadas a uma conta
     * @param size tamanho da pagina a ser buscada
     * @param page número da página a ser buscada
     * @param sort nome do campo e forma de ordenação (formato "field_name,ord",
     *             exemplo: "createdAt,asc" ou "createdAt,desc") dos elementos buscados
     * @param accountNumber número da conta
     * @return conjunto de todas as transferencias que a conta está relacionada
     */
    @Override
    public List<FinancialTransfer> findAllByAccount(int size, int page, String sort, String accountNumber) {
        log.info("m=findAllByAccount(), message=Search for all transfers related to account {}", accountNumber);
        PageRequest pageRequest = PageRequest.of(page, size, getSort(sort));
        Page<FinancialTransferEntity> financialTransfersEntityPage = financialTransferEntityRepository
                .findAllBySourceAccount_NumberOrDestinationAccount_Number(accountNumber, accountNumber, pageRequest);
        return financialTransfersEntityPage.getContent()
                .stream()
                .map(financialTransferEntity -> mapper.map(financialTransferEntity, FinancialTransfer.class))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param sortInfo campo com informações de ordenação (formato "field_name,ord",
     *      *             exemplo: "createdAt,asc" ou "createdAt,desc")
     * @return objeto Sort configurado com campo de ordenação e orientação (ascendencia ou descendencia)
     */
    private Sort getSort(String sortInfo) {
        log.info("m=getSort(), message=Sorting information retrieval, payload={}", sortInfo);
        if (sortInfo.contains(",") && (sortInfo.contains("asc") || sortInfo.contains("desc"))) {
            String[] sort = sortInfo.split(",");
            return sort[1].equalsIgnoreCase("asc")
                    ? Sort.by(sort[0]).ascending() : Sort.by(sort[0]).descending();
        } else {
            log.error("m=getSort(), message=Sorting information retrieval failed, payload={}", sortInfo);
            throw new BadRequestException(MessagePropertiesUtil
                    .getMessage("financial-transfer.error.invalid-sorting-parameter"));
        }
    }
}
