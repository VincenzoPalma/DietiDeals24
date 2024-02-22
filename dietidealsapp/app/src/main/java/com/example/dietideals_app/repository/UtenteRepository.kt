package com.example.dietideals_app.repository

import com.example.dietideals_app.model.dto.DatiProfiloUtente
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID

class UtenteRepository {
    suspend fun getDatiUtente(idUtente: UUID?): DatiProfiloUtente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<DatiProfiloUtente?>()
            ApiUtente.utenteService.getDatiUtente(idUtente).enqueue(object :
                Callback<DatiProfiloUtente> {
                override fun onResponse(
                    call: Call<DatiProfiloUtente>,
                    response: Response<DatiProfiloUtente>
                ) {
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        deferred.complete(risultato)
                        response.body()?.let {
                        }
                    }
                }

                override fun onFailure(call: Call<DatiProfiloUtente>, t: Throwable) {
                    deferred.complete(null)
                }
            })
            deferred.await()
        }
    }
}