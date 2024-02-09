package com.example.dietideals_app.model

class ContoCorrente(var nomeTitolare: String, var codiceBicSwift: String, var codiceIban: String){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ContoCorrente) return false

        if (nomeTitolare != other.nomeTitolare) return false
        if (codiceBicSwift != other.codiceBicSwift) return false
        return codiceIban == other.codiceIban
    }

    override fun hashCode(): Int {
        var result = nomeTitolare.hashCode()
        result = 31 * result + codiceBicSwift.hashCode()
        result = 31 * result + codiceIban.hashCode()
        return result
    }
}