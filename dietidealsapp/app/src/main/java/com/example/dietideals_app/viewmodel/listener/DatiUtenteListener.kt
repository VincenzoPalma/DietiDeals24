package com.example.dietideals_app.viewmodel.listener

import com.example.dietideals_app.model.dto.DatiProfiloUtente

interface DatiUtenteListener {
    fun onDataLoaded(datiUtente: DatiProfiloUtente?)
    fun onError()
}