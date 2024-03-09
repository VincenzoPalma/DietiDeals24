package com.example.dietideals_app.viewmodel.listener

import com.example.dietideals_app.model.Carta

interface CartaListener {

    fun onCarteLoaded(carte: List<Carta>)

    fun onCartaSaved(carta: Carta)

    fun onCartaDeleted()
    fun onError()
}