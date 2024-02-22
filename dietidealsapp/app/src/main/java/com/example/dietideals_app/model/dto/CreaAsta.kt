package com.example.dietideals_app.model.dto

import com.example.dietideals_app.model.enum.CategoriaAsta
import com.example.dietideals_app.model.enum.TipoAsta
import java.math.BigDecimal
import java.time.OffsetDateTime


class CreaAsta(val nome: String?,
               val descrizione: String?,
               val dataScadenza: OffsetDateTime?,
               val prezzoBase: BigDecimal?,
               val sogliaRialzo: BigDecimal?,
               val intervalloTempoOfferta: Int?,
               val categoria: CategoriaAsta,
               val tipo: TipoAsta)
{

}