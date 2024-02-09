package com.example.dietideals_app.presenter

import com.example.dietideals_app.model.Asta
import com.example.dietideals_app.model.enum.CategoriaAsta
import com.example.dietideals_app.model.enum.TipoAsta
import com.example.dietideals_app.repository.RetrofitConfig.apiService

class SchermataHomePresenter {

    suspend fun getAste(nome: String?, tipo: TipoAsta?, categoria : CategoriaAsta?): List<Asta> {
        try {
            val result: List<Asta> = apiService.getAste(page = 0, size = 12, nomeRicerca = nome, categoria = categoria, tipo = tipo)
            // Gestisci la risposta qui
            println(result.get(0).nome);
            return result;
        } catch (e: Exception) {
            // Gestisci eventuali errori qui
        }
        return emptyList();
    }

}