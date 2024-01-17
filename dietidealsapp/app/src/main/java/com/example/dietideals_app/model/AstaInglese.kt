package com.example.dietideals_app.model

import java.io.File
import java.math.BigDecimal
import java.time.LocalTime

class AstaInglese(titolo: String, categoria: String, descrizione: String, immagine: File, offerte: MutableSet<Offerta> = mutableSetOf(), notifiche: MutableSet<Notifica> = mutableSetOf(), val sogliaRialzo : BigDecimal = BigDecimal(10.00), val intervalloTempoOfferta : LocalTime = LocalTime.of(1, 0)) : Asta(titolo, categoria, descrizione, immagine, offerte, notifiche) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AstaInglese

        if (sogliaRialzo != other.sogliaRialzo) return false
        return intervalloTempoOfferta == other.intervalloTempoOfferta
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + sogliaRialzo.hashCode()
        result = 31 * result + intervalloTempoOfferta.hashCode()
        return result
    }
}