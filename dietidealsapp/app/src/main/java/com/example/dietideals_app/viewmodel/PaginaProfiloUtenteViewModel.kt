package com.example.dietideals_app.viewmodel

import com.example.dietideals_app.repository.UtenteRepository
import com.example.dietideals_app.viewmodel.listener.DatiUtenteListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class PaginaProfiloUtenteViewModel {

    private val utenteRepository = UtenteRepository()

    fun visualizzaDatiUtente(idUtente: UUID?, listener: DatiUtenteListener) {
        CoroutineScope(Dispatchers.IO).launch {
            val datiUtente = utenteRepository.getDatiUtente(idUtente)
            if (datiUtente != null) {
                listener.onDataLoaded(datiUtente)
            } else {
                listener.onError()
            }
        }
    }


}