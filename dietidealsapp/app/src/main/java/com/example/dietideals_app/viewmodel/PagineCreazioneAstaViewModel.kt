package com.example.dietideals_app.viewmodel

import com.example.dietideals_app.model.dto.CreaAsta
import com.example.dietideals_app.model.enum.RuoloUtente
import com.example.dietideals_app.repository.AstaRepository
import com.example.dietideals_app.repository.UtenteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PagineCreazioneAstaViewModel {

    private val astaRepository = AstaRepository()
    private val utenteRepository = UtenteRepository()

    fun inserisciAsta(creaAsta: CreaAsta) {
        CoroutineScope(Dispatchers.IO).launch {
            astaRepository.saveAsta(creaAsta)
        }
    }

    suspend fun isUtenteVenditore(): Boolean {
        var ruolo: RuoloUtente?
        withContext(Dispatchers.IO) {
            ruolo = utenteRepository.getRuoloUtente()
        }
        return ruolo == RuoloUtente.VENDITORE
    }

}