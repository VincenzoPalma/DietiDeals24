package com.example.dietideals_app.model

import java.io.File

class DatiVenditore(var partitaIva: String, var nomeTitolare: String, var codiceBicSwift: String, var codiceIban: String, var documentoRiconoscimento: File){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DatiVenditore

        if (partitaIva != other.partitaIva) return false
        if (nomeTitolare != other.nomeTitolare) return false
        if (codiceBicSwift != other.codiceBicSwift) return false
        if (codiceIban != other.codiceIban) return false
        return documentoRiconoscimento == other.documentoRiconoscimento
    }

    override fun hashCode(): Int {
        var result = partitaIva.hashCode()
        result = 31 * result + nomeTitolare.hashCode()
        result = 31 * result + codiceBicSwift.hashCode()
        result = 31 * result + codiceIban.hashCode()
        result = 31 * result + documentoRiconoscimento.hashCode()
        return result
    }
}