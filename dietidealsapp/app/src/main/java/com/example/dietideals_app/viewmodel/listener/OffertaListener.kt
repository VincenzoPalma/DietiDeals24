package com.example.dietideals_app.viewmodel.listener

import com.example.dietideals_app.model.Offerta

interface OffertaListener {

    fun onOfferteLoaded(offerte: List<Offerta>)

    fun onOffertaVincenteLoaded(offerta: Offerta)
    fun onError()
}