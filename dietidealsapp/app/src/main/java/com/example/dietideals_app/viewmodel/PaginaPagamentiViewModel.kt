package com.example.dietideals_app.viewmodel

import com.example.dietideals_app.model.dto.CreaCarta
import com.example.dietideals_app.repository.CartaRepository
import com.example.dietideals_app.viewmodel.listener.CartaListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class PaginaPagamentiViewModel {

    private val cartaRepository = CartaRepository()

    fun mostraCarte(listener: CartaListener) {
        CoroutineScope(Dispatchers.IO).launch {
            val carte = cartaRepository.getCarteUtente()
            if (carte != null) {
                listener.onCarteLoaded(carte)
            } else {
                listener.onError()
            }
        }
    }

    fun salvaCarta(creaCarta: CreaCarta, listener: CartaListener) {
        CoroutineScope(Dispatchers.IO).launch {
            val carta = cartaRepository.saveCarta(creaCarta)
            if (carta != null) {
                listener.onCartaSaved(carta)
            } else {
                listener.onError()
            }
        }
    }

    fun deleteCarta(idCarta: UUID, listener: CartaListener) {
        CoroutineScope(Dispatchers.IO).launch {
            val eliminate = cartaRepository.deleteCarta(idCarta)
            if (eliminate) {
                listener.onCartaDeleted()
            } else {
                listener.onError()
            }
        }
    }
}