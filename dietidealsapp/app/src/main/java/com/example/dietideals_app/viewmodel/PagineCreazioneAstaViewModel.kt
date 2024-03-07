package com.example.dietideals_app.viewmodel

import com.example.dietideals_app.model.dto.CreaAsta
import com.example.dietideals_app.repository.AstaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PagineCreazioneAstaViewModel {

    private val astaRepository = AstaRepository()

    fun inserisciAsta(creaAsta: CreaAsta){
        CoroutineScope(Dispatchers.IO).launch {
            astaRepository.saveAsta(creaAsta)
        }
    }
}