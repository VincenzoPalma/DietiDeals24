package com.example.dietideals_app.viewmodel.listener

import com.example.dietideals_app.model.Utente
import com.example.dietideals_app.model.dto.DatiProfiloUtente

interface DatiUtenteListener {
    fun onDataLoaded(datiUtente: DatiProfiloUtente?)

    fun onDatiModificati(utente: Utente)

    fun onError()
}