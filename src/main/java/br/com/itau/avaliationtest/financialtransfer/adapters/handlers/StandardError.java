package br.com.itau.avaliationtest.financialtransfer.adapters.handlers;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
