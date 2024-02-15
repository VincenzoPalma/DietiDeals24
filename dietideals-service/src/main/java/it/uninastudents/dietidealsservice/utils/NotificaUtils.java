package it.uninastudents.dietidealsservice.utils;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class NotificaUtils {

    public static String buildMessaggioOffertaSuperata(String nomeAsta, BigDecimal prezzo) {
        return "La tua offerta per " + nomeAsta + " è stata superata per " + prezzo.toString() + "€.";
    }
}
