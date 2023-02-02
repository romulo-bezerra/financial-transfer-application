package br.com.itau.avaliationtest.financialtransfer.adapters.outbound.persistence.enums;

public enum TransferType {

    TED("TED"),
    DOC("DOC"),
    PIX("PIX");

    private String type;

    TransferType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
