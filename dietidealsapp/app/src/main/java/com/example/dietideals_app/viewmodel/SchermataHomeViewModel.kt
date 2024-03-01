package com.example.dietideals_app.viewmodel

import com.example.dietideals_app.model.enum.CategoriaAsta
import com.example.dietideals_app.model.enum.TipoAsta
import com.example.dietideals_app.repository.AstaRepository
import com.example.dietideals_app.viewmodel.listener.AsteListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SchermataHomeViewModel {

    private val astaRepostory = AstaRepository()

    fun mostraAste(page : Int, nome: String?, categoriaAsta: CategoriaAsta?, tipoAsta: TipoAsta?, listener: AsteListener){
        CoroutineScope(Dispatchers.IO).launch {
            val aste = astaRepostory.getAste(nome, categoriaAsta, tipoAsta)
            if (aste != null) {
                listener.onAsteLoaded(aste)
            } else {
                listener.onError()
            }
        }
    }
}