package com.example.dietideals_app.viewmodel

import com.example.dietideals_app.model.ContoCorrente
import com.example.dietideals_app.model.dto.CreaContoCorrente
import com.example.dietideals_app.repository.ContoCorrenteRepository
import com.example.dietideals_app.viewmodel.listener.ContoCorrenteListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaginaDatiVenditoreViewModel {

    private val contoCorrenteRepository = ContoCorrenteRepository()

    fun saveContoCorrente(creaContoCorrente: CreaContoCorrente, listener: ContoCorrenteListener) {
        CoroutineScope(Dispatchers.IO).launch {
            val contoCorrente = contoCorrenteRepository.saveContoCorrente(creaContoCorrente)
            if (contoCorrente != null) {
                listener.onContoCorrenteSaved(contoCorrente)
            } else {
                listener.onError()
            }
        }
    }

    fun modifyContoCorrente(contoCorrente: ContoCorrente, listener: ContoCorrenteListener) {
        CoroutineScope(Dispatchers.IO).launch {
            val risultato = contoCorrenteRepository.modifyContoCorrente(contoCorrente)
            if (risultato != null) {
                listener.onContoCorrenteSaved(risultato)
            } else {
                listener.onError()
            }
        }
    }

    fun getContoCorrente(listener: ContoCorrenteListener) {
        CoroutineScope(Dispatchers.IO).launch {
            val contoCorrente = contoCorrenteRepository.getContoCorrente()
            if (contoCorrente != null) {
                listener.onContoCorrenteLoaded(contoCorrente)
            } else {
                listener.onError()
            }
        }
    }

}