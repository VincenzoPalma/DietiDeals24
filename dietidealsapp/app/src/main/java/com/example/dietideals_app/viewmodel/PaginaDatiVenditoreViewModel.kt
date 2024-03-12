package com.example.dietideals_app.viewmodel

import com.example.dietideals_app.model.ContoCorrente
import com.example.dietideals_app.model.dto.CreaContoCorrente
import com.example.dietideals_app.model.enum.RuoloUtente
import com.example.dietideals_app.repository.ContoCorrenteRepository
import com.example.dietideals_app.repository.UtenteRepository
import com.example.dietideals_app.viewmodel.listener.ContoCorrenteListener
import com.example.dietideals_app.viewmodel.listener.UtenteListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaginaDatiVenditoreViewModel {

    private val contoCorrenteRepository = ContoCorrenteRepository()
    private val utenteRepository = UtenteRepository()

    fun saveContoCorrente(creaContoCorrente: CreaContoCorrente) {
        CoroutineScope(Dispatchers.IO).launch {
            contoCorrenteRepository.saveContoCorrente(creaContoCorrente)
        }
    }

    fun modifyContoCorrente(contoCorrente: ContoCorrente) {
        CoroutineScope(Dispatchers.IO).launch {
            contoCorrenteRepository.modifyContoCorrente(contoCorrente)
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

    suspend fun isUtenteVenditore(listener: UtenteListener): Boolean {
        var ruolo: RuoloUtente?
        withContext(Dispatchers.IO) {
            ruolo = utenteRepository.getRuoloUtente()
            if (ruolo != null) {
                listener.onRuoloLoaded(ruolo!!)
            } else {
                listener.onError()
            }
        }
        return ruolo == RuoloUtente.VENDITORE
    }

    fun modificaPartitaIva(partitaIva: String) {
        CoroutineScope(Dispatchers.IO).launch {
            utenteRepository.modificaPartitaIva(partitaIva)
        }
    }

    fun modificaDocumentoVenditore(urlDocumento: String) {
        CoroutineScope(Dispatchers.IO).launch {
            utenteRepository.modificaDocumentoVenditore(urlDocumento)
        }
    }

    suspend fun getPartitaIva(listener: UtenteListener) {
        withContext(Dispatchers.IO) {
            val partitaIva = utenteRepository.getPartitaIva()
            if (partitaIva != null) {
                listener.onPartitaIvaLoaded(partitaIva)
            } else {
                listener.onError()
            }
        }
    }

    fun setUtenteVenditore() {
        CoroutineScope(Dispatchers.IO).launch {
            utenteRepository.setUtenteVenditore()
        }
    }
}