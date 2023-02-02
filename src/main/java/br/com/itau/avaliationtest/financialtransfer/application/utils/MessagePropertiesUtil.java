package br.com.itau.avaliationtest.financialtransfer.application.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ResourceBundle;

@Slf4j
public class MessagePropertiesUtil {

    private MessagePropertiesUtil() {

    }

    private static final ResourceBundle errorMessages = ResourceBundle.getBundle("messages");

    public static String getMessage(String label) {
        if (errorMessages.containsKey(label)) {
            return errorMessages.getString(label);
        }
        log.info("m=getMessage(), message=[{}] label not found", label);
        return "";
    }
}
