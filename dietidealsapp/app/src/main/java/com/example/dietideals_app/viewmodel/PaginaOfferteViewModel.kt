package com.example.dietideals_app.viewmodel

import com.example.dietideals_app.model.enum.StatoOfferta
import com.example.dietideals_app.repository.OffertaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.UUID

class PaginaOfferteViewModel {

    private val offertaRepository = OffertaRepository()
    val paginaAstaViewModel = PaginaAstaViewModel()

    fun makeOffertaVincente(idOfferta: UUID){
        CoroutineScope(Dispatchers.IO).launch {
            offertaRepository.modificaStatoOfferta(idOfferta, StatoOfferta.VINCENTE)
        }
    }

    fun makeOffertaRifiutata(idOfferta: UUID){
        CoroutineScope(Dispatchers.IO).launch {
            offertaRepository.modificaStatoOfferta(idOfferta, StatoOfferta.RIFIUTATA)
        }
    }

}