package com.example.dietideals_app.model

import com.example.dietideals_app.model.enum.CategoriaAsta
import com.example.dietideals_app.model.enum.StatoAsta
import com.example.dietideals_app.model.enum.TipoAsta
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID


class Asta(
    val nome: String, val descrizione: String, val urlFoto: String,
    val dataScadenza: OffsetDateTime?, val prezzoBase: BigDecimal,
    val sogliaRialzo: BigDecimal?, val intervalloTempoOfferta: Int?,
    val categoria: CategoriaAsta, val tipo: TipoAsta, val stato: StatoAsta,
    val proprietario: Utente, val id: UUID, val creationDate: OffsetDateTime
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Asta) return false

        if (nome != other.nome) return false
        if (descrizione != other.descrizione) return false
        if (urlFoto != other.urlFoto) return false
        if (dataScadenza != other.dataScadenza) return false
        if (prezzoBase != other.prezzoBase) return false
        if (sogliaRialzo != other.sogliaRialzo) return false
        if (intervalloTempoOfferta != other.intervalloTempoOfferta) return false
        if (categoria != other.categoria) return false
        if (tipo != other.tipo) return false
        if (stato != other.stato) return false
        if (proprietario != other.proprietario) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nome.hashCode()
        result = 31 * result + descrizione.hashCode()
        result = 31 * result + urlFoto.hashCode()
        result = 31 * result + dataScadenza.hashCode()
        result = 31 * result + prezzoBase.hashCode()
        result = 31 * result + sogliaRialzo.hashCode()
        result = 31 * result + intervalloTempoOfferta.hashCode()
        result = 31 * result + categoria.hashCode()
        result = 31 * result + tipo.hashCode()
        result = 31 * result + stato.hashCode()
        result = 31 * result + proprietario.hashCode()
        return result
    }
}