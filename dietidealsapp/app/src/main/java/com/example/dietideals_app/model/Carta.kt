package com.example.dietideals_app.model

import java.time.LocalDate

class Carta(val numero: String, val nomeTitolare: String, val codiceCvvCvc: Short, val scadenza: LocalDate){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Carta

        if (numero != other.numero) return false
        if (nomeTitolare != other.nomeTitolare) return false
        if (codiceCvvCvc != other.codiceCvvCvc) return false
        return scadenza == other.scadenza
    }

    override fun hashCode(): Int {
        var result = numero.hashCode()
        result = 31 * result + nomeTitolare.hashCode()
        result = 31 * result + codiceCvvCvc
        result = 31 * result + scadenza.hashCode()
        return result
    }
}