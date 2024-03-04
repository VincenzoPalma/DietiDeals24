package com.example.dietideals_app.viewmodel

import com.example.dietideals_app.model.dto.DatiProfiloUtente
import com.example.dietideals_app.repository.UtenteRepository
import com.example.dietideals_app.viewmodel.listener.DatiUtenteListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaginaModificaProfiloUtenteViewModel {

    private val utenteRepository = UtenteRepository()

    fun modificaDatiUtente(datiProfiloUtente: DatiProfiloUtente, listener: DatiUtenteListener) {
        CoroutineScope(Dispatchers.IO).launch {
            val utente = utenteRepository.modificaDatiUtente(datiProfiloUtente)
            if (utente != null) {
                listener.onDatiModificati(utente)
            } else {
                listener.onError()
            }
        }
    }
}