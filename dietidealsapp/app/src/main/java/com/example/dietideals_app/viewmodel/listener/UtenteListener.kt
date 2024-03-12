package com.example.dietideals_app.viewmodel.listener

import com.example.dietideals_app.model.enum.RuoloUtente

interface UtenteListener {

    fun onRuoloLoaded(ruolo: RuoloUtente)

    fun onPartitaIvaLoaded(partitaIvaUtente: String)
    fun onError()

}