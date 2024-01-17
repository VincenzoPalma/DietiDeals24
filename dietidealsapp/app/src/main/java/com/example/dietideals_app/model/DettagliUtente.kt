package com.example.dietideals_app.model

class DettagliUtente(var descrizione: String, var sitoWeb: String, var indirizzo: String, var instagram: String, var facebook: String, var twitter: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DettagliUtente

        if (descrizione != other.descrizione) return false
        if (sitoWeb != other.sitoWeb) return false
        if (indirizzo != other.indirizzo) return false
        if (instagram != other.instagram) return false
        if (facebook != other.facebook) return false
        return twitter == other.twitter
    }

    override fun hashCode(): Int {
        var result = descrizione.hashCode()
        result = 31 * result + sitoWeb.hashCode()
        result = 31 * result + indirizzo.hashCode()
        result = 31 * result + instagram.hashCode()
        result = 31 * result + facebook.hashCode()
        result = 31 * result + twitter.hashCode()
        return result
    }
}