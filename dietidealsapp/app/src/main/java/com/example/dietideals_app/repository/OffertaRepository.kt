package com.example.dietideals_app.repository

import com.example.dietideals_app.model.Offerta
import com.example.dietideals_app.model.enum.StatoOfferta
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
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

    suspend fun modificaStatoOfferta(idOfferta: UUID, statoOfferta: StatoOfferta): Offerta? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Offerta?>()
            ApiOfferta.offertaService.modificaStatoOfferta(idOfferta, statoOfferta).enqueue(object :
                Callback<Offerta> {
                override fun onResponse(
                    call: Call<Offerta>,
                    response: Response<Offerta>
                ) {
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        deferred.complete(risultato)
                    }
                }

                override fun onFailure(call: Call<Offerta>, t: Throwable) {
                    deferred.complete(null)
                }
            })
            deferred.await()
        }
    }

    suspend fun saveOfferta(astaId: UUID, prezzo: BigDecimal): Offerta? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Offerta?>()
            ApiOfferta.offertaService.saveOfferta(astaId, prezzo).enqueue(object :
                Callback<Offerta?> {
                override fun onResponse(
                    call: Call<Offerta?>,
                    response: Response<Offerta?>
                ) {
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        if (risultato != null) {
                            deferred.complete(risultato)
                        }
                    }
                }

                override fun onFailure(call: Call<Offerta?>, t: Throwable) {
                    deferred.complete(null)
                }
            })
            deferred.await()
        }
    }
}