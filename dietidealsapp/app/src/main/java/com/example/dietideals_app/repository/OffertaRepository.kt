package com.example.dietideals_app.repository

import com.example.dietideals_app.model.Offerta
import com.example.dietideals_app.model.enum.StatoOfferta
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID

class OffertaRepository {

    suspend fun getOfferteAsta(astaId: UUID, statoOfferta: StatoOfferta): List<Offerta>? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<List<Offerta>?>()
            ApiOfferta.offertaService.getOfferte(astaId, statoOfferta).enqueue(object :
                Callback<List<Offerta>> {
                override fun onResponse(
                    call: Call<List<Offerta>>,
                    response: Response<List<Offerta>>
                ) {
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        deferred.complete(risultato)
                    }
                }

                override fun onFailure(call: Call<List<Offerta>>, t: Throwable) {
                    deferred.complete(null)
                }
            })
            deferred.await()
        }
    }
}