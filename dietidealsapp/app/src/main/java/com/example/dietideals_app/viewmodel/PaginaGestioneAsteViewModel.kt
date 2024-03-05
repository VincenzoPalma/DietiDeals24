package com.example.dietideals_app.viewmodel

import com.example.dietideals_app.model.enum.StatoAsta
import com.example.dietideals_app.repository.AstaRepository
import com.example.dietideals_app.viewmodel.listener.AsteListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaginaGestioneAsteViewModel {

    private val astaRepository = AstaRepository()

    fun getAsteUtenteAttive(numeroPagina : Int, listener: AsteListener){
        CoroutineScope(Dispatchers.IO).launch {
            val aste = astaRepository.getAsteUtente(numeroPagina, StatoAsta.ATTIVA)
            if (aste != null) {
                listener.onAsteAttiveUtenteLoaded(aste)
            } else {
                listener.onError()
            }
        }
    }

    fun getAsteUtenteTerminate(numeroPagina : Int, listener: AsteListener){
        CoroutineScope(Dispatchers.IO).launch {
            val aste = astaRepository.getAsteUtente(numeroPagina, StatoAsta.TERMINATA)
            if (aste != null) {
                listener.onAsteTerminateUtenteLoaded(aste)
            } else {
                listener.onError()
            }
        }
    }

    fun getAsteUtenteSeguite(numeroPagina : Int, listener: AsteListener){
        CoroutineScope(Dispatchers.IO).launch {
            val aste = astaRepository.getAstePartecipateUtente(numeroPagina, false)
            if (aste != null) {
                listener.onAsteSeguiteUtenteLoaded(aste)
            } else {
                listener.onError()
            }
        }
    }

    fun getAsteUtenteVinte(numeroPagina : Int, listener: AsteListener){
        CoroutineScope(Dispatchers.IO).launch {
            val aste = astaRepository.getAstePartecipateUtente(numeroPagina, true)
            if (aste != null) {
                listener.onAsteVinteUtenteLoaded(aste)
            } else {
                listener.onError()
            }
        }
    }
}