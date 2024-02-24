package com.example.dietideals_app.presenter

import com.example.dietideals_app.model.dto.DatiProfiloUtente
import com.example.dietideals_app.repository.UtenteRepository
import java.util.UUID

class PaginaProfiloUtentePresenter {

    private val repositoryUtente = UtenteRepository()

    suspend fun visualizzaDatiUtente(idUtente : UUID?) : DatiProfiloUtente? {
        return repositoryUtente.getDatiUtente(idUtente)
    }
}