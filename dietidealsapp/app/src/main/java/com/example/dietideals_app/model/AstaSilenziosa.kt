package com.example.dietideals_app.model

import java.io.File
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

class AstaSilenziosa(titolo: String, categoria: String, descrizione: String, immagine: File, offerte: MutableSet<Offerta> = mutableSetOf(), notifiche: MutableSet<Notifica> = mutableSetOf(), val prezzoBase : BigDecimal, val dataScadenza : LocalDate, val orarioScadenza : LocalTime) : Asta(titolo, categoria, descrizione, immagine, offerte, notifiche){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AstaSilenziosa

        if (prezzoBase != other.prezzoBase) return false
        if (dataScadenza != other.dataScadenza) return false
        return orarioScadenza == other.orarioScadenza
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + prezzoBase.hashCode()
        result = 31 * result + dataScadenza.hashCode()
        result = 31 * result + orarioScadenza.hashCode()
        return result
    }
}