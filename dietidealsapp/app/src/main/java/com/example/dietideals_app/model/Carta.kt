package com.example.dietideals_app.model

import java.util.UUID

class Carta(
    val id: UUID,
    val numero: String,
    val nomeTitolare: String,
    val codiceCvvCvc: String,
    val dataScadenza: String,
    val utente: Utente
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Carta) return false

        if (numero != other.numero) return false
        if (nomeTitolare != other.nomeTitolare) return false
        if (codiceCvvCvc != other.codiceCvvCvc) return false
        if (dataScadenza != other.dataScadenza) return false
        return utente == other.utente
    }

    override fun hashCode(): Int {
        var result = numero.hashCode()
        result = 31 * result + nomeTitolare.hashCode()
        result = 31 * result + codiceCvvCvc.hashCode()
        result = 31 * result + dataScadenza.hashCode()
        result = 31 * result + utente.hashCode()
        return result
    }
}