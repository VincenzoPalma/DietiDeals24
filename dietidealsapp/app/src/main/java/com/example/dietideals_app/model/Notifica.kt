package com.example.dietideals_app.model

class Notifica(val contenuto : String, val utente: Utente, val asta: Asta) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Notifica) return false

        if (contenuto != other.contenuto) return false
        if (utente != other.utente) return false
        return asta == other.asta
    }

    override fun hashCode(): Int {
        var result = contenuto.hashCode()
        result = 31 * result + utente.hashCode()
        result = 31 * result + asta.hashCode()
        return result
    }
}
