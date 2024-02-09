package com.example.dietideals_app.model

import com.example.dietideals_app.model.enum.StatoOfferta
import java.util.Currency

class Offerta (val prezzo : Currency, val stato : StatoOfferta, val utente: Utente, val asta: Asta){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Offerta) return false

        if (prezzo != other.prezzo) return false
        if (stato != other.stato) return false
        if (utente != other.utente) return false
        return asta == other.asta
    }

    override fun hashCode(): Int {
        var result = prezzo.hashCode()
        result = 31 * result + stato.hashCode()
        result = 31 * result + utente.hashCode()
        result = 31 * result + asta.hashCode()
        return result
    }
}
