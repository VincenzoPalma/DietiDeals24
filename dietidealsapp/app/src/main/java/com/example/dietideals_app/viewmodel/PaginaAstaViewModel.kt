package com.example.dietideals_app.viewmodel

import com.example.dietideals_app.model.enum.StatoOfferta
import com.example.dietideals_app.repository.OffertaRepository
import com.example.dietideals_app.repository.UtenteRepository
import com.example.dietideals_app.viewmodel.listener.OffertaListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class PaginaAstaViewModel {

    private val offertaRepository = OffertaRepository()
    private val utenteRepository = UtenteRepository()

    fun getOffertaVincente(idAsta: UUID, listener: OffertaListener) {
        CoroutineScope(Dispatchers.IO).launch {
            val listOfferte = offertaRepository.getOfferteAsta(idAsta, StatoOfferta.VINCENTE)
            if (!listOfferte.isNullOrEmpty()) {
                val offerta = listOfferte[0]
                listener.onOffertaVincenteLoaded(offerta)
            }
        }
    }

    fun getOfferte(idAsta: UUID, listener: OffertaListener) {
        CoroutineScope(Dispatchers.IO).launch {
            val listOfferte = offertaRepository.getOfferteAsta(idAsta, StatoOfferta.NON_VINCENTE)
            if (listOfferte != null) {
                listener.onOfferteLoaded(listOfferte)
            } else {
                listener.onError()
            }
        }
    }

    suspend fun getIdUtenteFromIdAuth(idAuth: String) : UUID? {
        val utente = utenteRepository.getUtenteByIdAuth(idAuth)
        return utente?.id
    }
}