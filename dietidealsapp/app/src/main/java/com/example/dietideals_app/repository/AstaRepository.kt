package com.example.dietideals_app.repository

import com.example.dietideals_app.model.Asta
import com.example.dietideals_app.model.enum.CategoriaAsta
import com.example.dietideals_app.model.enum.TipoAsta
import com.example.dietideals_app.model.list.AstaList
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AstaRepository {

    suspend fun getAste(nome: String?, categoria: CategoriaAsta?, tipo: TipoAsta?): List<Asta>? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<List<Asta>?>()

            ApiAsta.astaService.getAste(0, 12, nome, tipo, categoria).enqueue(object :
                Callback<AstaList> {
                override fun onResponse(
                    call: Call<AstaList>,
                    response: Response<AstaList>
                ) {
                    if (response.isSuccessful) {
                        val risultato : List<Asta>? = response.body()?.content
                        deferred.complete(risultato)
                    } else {
                        deferred.complete(null)
                    }
                }

                override fun onFailure(call: Call<AstaList>, t: Throwable) {
                    deferred.complete(null)
                }
            })

            deferred.await()
        }
    }

}