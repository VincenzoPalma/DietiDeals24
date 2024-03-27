package com.example.dietideals_app.model

import java.util.UUID

class ContoCorrente(
    var id: UUID?,
    var nomeTitolare: String,
    var codiceBicSwift: String,
    var iban: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ContoCorrente) return false

        if (nomeTitolare != other.nomeTitolare) return false
        if (codiceBicSwift != other.codiceBicSwift) return false
        return iban == other.iban
    }

    override fun hashCode(): Int {
        var result = nomeTitolare.hashCode()
        result = 31 * result + codiceBicSwift.hashCode()
        result = 31 * result + iban.hashCode()
        return result
    }
}