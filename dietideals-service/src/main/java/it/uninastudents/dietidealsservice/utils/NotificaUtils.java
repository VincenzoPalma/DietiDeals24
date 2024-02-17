package it.uninastudents.dietidealsservice.utils;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class NotificaUtils {

    public static String buildMessaggioOffertaSuperata(String nomeAsta, BigDecimal prezzo) {
        return "La tua offerta per " + nomeAsta + " è stata superata per " + prezzo.toString() + "€.";
    }

    public static String buildMessaggioAstaTerminataSenzaOfferte(String nomeAsta) {
        return "La tua asta " + nomeAsta + " è terminata senza offerte.";
    }

    public static String buildMessaggioAstaTerminataUtenteVincitore(String nomeAsta, BigDecimal prezzo) {
        return "Congratulazioni! Ti sei aggiudicato l'asta " + nomeAsta + "per " + prezzo.toString() + "€.";
    }

    public static String buildMessaggioAstaTerminataUtenteNonVincitore(String nomeAsta) {
        return "L'asta " + nomeAsta + "a cui hai partecipato è terminata.";
    }
}
