package com.example.dietideals_app.viewmodel.listener

import com.example.dietideals_app.model.Asta

interface AsteListener {
    fun onAsteLoaded(aste: List<Asta>)

    fun onAsteAttiveUtenteLoaded(aste: List<Asta>)

    fun onAsteTerminateUtenteLoaded(aste: List<Asta>)

    fun onAsteSeguiteUtenteLoaded(aste: List<Asta>)

    fun onAsteVinteUtenteLoaded(aste: List<Asta>)

    fun onError()
}