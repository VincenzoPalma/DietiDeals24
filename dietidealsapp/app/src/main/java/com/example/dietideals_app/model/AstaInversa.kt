package com.example.dietideals_app.model

import java.io.File
import java.math.BigDecimal
import java.time.LocalDate

class AstaInversa(titolo: String, categoria: String, descrizione: String, immagine: File, offerte: MutableSet<Offerta> = mutableSetOf(), notifiche: MutableSet<Notifica> = mutableSetOf(), val prezzoMassimo : BigDecimal, val dataScadenza : LocalDate) : Asta(titolo, categoria, descrizione, immagine, offerte, notifiche) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AstaInversa

        if (prezzoMassimo != other.prezzoMassimo) return false
        return dataScadenza == other.dataScadenza
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + prezzoMassimo.hashCode()
        result = 31 * result + dataScadenza.hashCode()
        return result
    }
}