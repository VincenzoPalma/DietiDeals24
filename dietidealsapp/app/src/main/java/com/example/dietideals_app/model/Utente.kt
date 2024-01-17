package com.example.dietideals_app.model

import java.io.File
import java.time.LocalDate

class Utente(val username: String, val email: String, val password: String, val nome: String, val cognome: String, val dataDiNascita: LocalDate, var immagineProfilo: File, var datiVenditore: DatiVenditore?, var dettagliUtente: DettagliUtente?, var carteAssociate: MutableSet<Carta> = mutableSetOf(), var aste: MutableSet<Asta> = mutableSetOf(), var offerte: MutableSet<Offerta> = mutableSetOf(), var notifiche: MutableSet<Notifica> = mutableSetOf()){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Utente

        if (username != other.username) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (nome != other.nome) return false
        if (cognome != other.cognome) return false
        if (dataDiNascita != other.dataDiNascita) return false
        if (immagineProfilo != other.immagineProfilo) return false
        if (datiVenditore != other.datiVenditore) return false
        if (dettagliUtente != other.dettagliUtente) return false
        if (carteAssociate != other.carteAssociate) return false
        if (aste != other.aste) return false
        if (offerte != other.offerte) return false
        return notifiche == other.notifiche
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + nome.hashCode()
        result = 31 * result + cognome.hashCode()
        result = 31 * result + dataDiNascita.hashCode()
        result = 31 * result + immagineProfilo.hashCode()
        result = 31 * result + (datiVenditore?.hashCode() ?: 0)
        result = 31 * result + (dettagliUtente?.hashCode() ?: 0)
        result = 31 * result + carteAssociate.hashCode()
        result = 31 * result + aste.hashCode()
        result = 31 * result + offerte.hashCode()
        result = 31 * result + notifiche.hashCode()
        return result
    }
}