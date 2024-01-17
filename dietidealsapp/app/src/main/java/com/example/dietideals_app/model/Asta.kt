package com.example.dietideals_app.model

import java.io.File

open class Asta(val titolo: String, val categoria: String, val descrizione: String, val immagine: File, var offerte: MutableSet<Offerta> = mutableSetOf(), var notifiche: MutableSet<Notifica> = mutableSetOf()){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Asta

        if (titolo != other.titolo) return false
        if (categoria != other.categoria) return false
        if (descrizione != other.descrizione) return false
        if (immagine != other.immagine) return false
        if (offerte != other.offerte) return false
        return notifiche == other.notifiche
    }

    override fun hashCode(): Int {
        var result = titolo.hashCode()
        result = 31 * result + categoria.hashCode()
        result = 31 * result + descrizione.hashCode()
        result = 31 * result + immagine.hashCode()
        result = 31 * result + offerte.hashCode()
        result = 31 * result + notifiche.hashCode()
        return result
    }
}