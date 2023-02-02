package br.com.itau.avaliationtest.financialtransfer.application.services;

import br.com.itau.avaliationtest.financialtransfer.adapters.dtos.requests.FinancialTransferRequest;
import br.com.itau.avaliationtest.financialtransfer.adapters.exceptions.AccountNotExistException;
import br.com.itau.avaliationtest.financialtransfer.adapters.exceptions.TransferBlockedException;
import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities.AccountEntity;
import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities.FinancialTransferEntity;
import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.entities.FinancialTransferEntity.FinancialTransferEntityBuilder;
import br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.enums.TransferType;
import br.com.itau.avaliationtest.financialtransfer.application.domain.FinancialTransfer;
import br.com.itau.avaliationtest.financialtransfer.application.ports.repository.AccountRepositoryPort;
import br.com.itau.avaliationtest.financialtransfer.application.ports.repository.FinancialTransferRepositoryPort;
import br.com.itau.avaliationtest.financialtransfer.application.ports.service.FinancialTransferServicePort;
import br.com.itau.avaliationtest.financialtransfer.application.utils.MessagePropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FinancialTransferServiceImpl implements FinancialTransferServicePort {

    @Value(value = "${config.business-params.allowed-transfer-amount.maximum.anyone}")
    private BigDecimal maximumTransferAmountAllowed;

    private final AccountRepositoryPort accountRepositoryPort;

    private final FinancialTransferRepositoryPort financialTransferRepositoryPort;

    private FinancialTransferEntityBuilder financialTransferEntityBuilder;

    public FinancialTransferServiceImpl(AccountRepositoryPort accountRepositoryPort,
                                        FinancialTransferRepositoryPort financialTransferRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.financialTransferRepositoryPort = financialTransferRepositoryPort;
    }

    @Override
    public FinancialTransfer performTransfer(FinancialTransferRequest financialTransferRequest) {
        log.info("m=performTransfer(), message=Start of transfer, payload={}", financialTransferRequest);
        Optional<AccountEntity> sourceAccountOptional = accountRepositoryPort
                .findByNumber(financialTransferRequest.getSourceAccountNumber());
        Optional<AccountEntity> destinationAccountOptional = accountRepositoryPort
                .findByNumber(financialTransferRequest.getDestinationAccountNumber());

        checkExistenceOfAccounts(sourceAccountOptional, destinationAccountOptional);

        var sourceAccount = sourceAccountOptional.get();
        var destinationAccount = destinationAccountOptional.get();
        var transferAmount = financialTransferRequest.getValue();

        financialTransferEntityBuilder = FinancialTransferEntity.builder()
                .type(TransferType.valueOf(financialTransferRequest.getType()))
                .value(transferAmount)
                .sourceAccount(sourceAccount)
                .destinationAccount(destinationAccount);

        String exceptionMessage = "";

        if (isTransferableValue(transferAmount)) {
            if (accountHasBalance(sourceAccount, transferAmount)) {
                return performTransferOperations(sourceAccount, destinationAccount, transferAmount);
            } else {
                log.error("m=performTransfer(), message=Transfer failed. Account without bank balance, payload={}", financialTransferRequest);
                exceptionMessage = MessagePropertiesUtil
                        .getMessage("financial-transfer.error.account-without-balance");
            }
        } else {
            log.error("m=performTransfer(), message=Transfer failed. Transfer amount above allowed, payload={}", financialTransferRequest);
            exceptionMessage = MessagePropertiesUtil
                    .getMessage("financial-transfer.error.value-above-allowed");
        }

        financialTransferRepositoryPort.save(financialTransferEntityBuilder
                .success(Boolean.FALSE)
                .build());

        throw new TransferBlockedException(exceptionMessage);
    }

    private void checkExistenceOfAccounts(Optional<AccountEntity> sourceAccount, Optional<AccountEntity> destinationAccount) {
        if (!Boolean.logicalAnd(sourceAccount.isPresent(), destinationAccount.isPresent())) {
            throw new AccountNotExistException(MessagePropertiesUtil
                    .getMessage("financial-transfer.error.account-not-exist"));
        }
    }

    @Override
    public List<FinancialTransfer> listPagedTransfersSortedByAccount(int size, int page, String sort, String accountNumber) {
        return financialTransferRepositoryPort.findAllByAccount(size, page, sort, accountNumber);
    }

    /**
     * Responsalvel por invocar a operação de debitar e creditar de forma isolada, transacionalmente,
     * em contas e fazer um save no banco da transferencia
     * @param sourceAccount conta origem a ser realizado o debito pela transferencia
     * @param destinationAccount conta destino a ser realizado o credito pela transferencia
     * @param amount valor da transferencia
     * @return objeto de transferencia financeira contendo entre outros os dados de conta debitada e creditada
     */
    @Transactional
    FinancialTransfer performTransferOperations(AccountEntity sourceAccount, AccountEntity destinationAccount, BigDecimal amount) {
        debitAccount(sourceAccount, amount);
        creditAccount(destinationAccount, amount);
        return financialTransferRepositoryPort.save(financialTransferEntityBuilder
                .success(Boolean.TRUE)
                .build());
    }

    /**
     * Responsavel por realizar o debito na conta
     * @param sourceAccount conta a ser debitada
     * @param amount total a ser debitado
     */
    private void debitAccount(AccountEntity sourceAccount, BigDecimal amount) {
        log.info("m=debitAccount(), message=Start of account debit operation");
        var sourceAccountCurrentBalance = sourceAccount.getBalance();
        var sourceAccountBalanceDebited = sourceAccountCurrentBalance.subtract(amount);
        sourceAccount.setBalance(sourceAccountBalanceDebited);
        accountRepositoryPort.save(sourceAccount);
    }

    /**
     * Responsavel por realizar o crdito na conta
     * @param destinationAccount conta a ser creditada
     * @param amount total a ser creditado
     */
    private void creditAccount(AccountEntity destinationAccount, BigDecimal amount) {
        log.info("m=creditAccount(), message=Start of account credit operation");
        var destinationAccountCurrentBalance = destinationAccount.getBalance();
        var destinationAccountBalanceCredited = destinationAccountCurrentBalance.add(amount);
        destinationAccount.setBalance(destinationAccountBalanceCredited);
        accountRepositoryPort.save(destinationAccount);
    }

    /**
     * Responsavel pela verificação de saldo na conta para a transferencia
     * @param sourceAccount conta a ser verificada o saldo
     * @param transferAmount valor da transferencia
     * @return true caso a conta possua saldo maior que o valor da transferencia, false para o contrario
     */
    private boolean accountHasBalance(AccountEntity sourceAccount, BigDecimal transferAmount) {
        log.info("m=accountHasBalance(), message=Account balance check");
        return sourceAccount.getBalance().compareTo(transferAmount) >= 0;
    }

    /**
     * Responsavel pela verificação de possibilidade de transferencia com base no valor limite parametrizado
     * negociado configurado na aplicação como regra de domínio
     * @param transferAmount valor da transferencia
     * @return true caso o valor seja menor ou igual ao limite de transferencia configurado, false caso contrario
     */
    private boolean isTransferableValue(BigDecimal transferAmount) {
        log.info("m=isTransferableValue(), message=Transfer amount permission check");
        return maximumTransferAmountAllowed.compareTo(transferAmount) >= 0;
    }

}
