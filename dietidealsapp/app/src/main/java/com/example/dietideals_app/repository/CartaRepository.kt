package com.example.dietideals_app.repository

import com.example.dietideals_app.model.Carta
import com.example.dietideals_app.model.dto.CreaCarta
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID

class CartaRepository {

    suspend fun getCarteUtente(): List<Carta>? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<List<Carta>?>()

            ApiCarta.cartaService.getCarteUtente().enqueue(object :
                Callback<List<Carta>> {
                override fun onResponse(
                    call: Call<List<Carta>>,
                    response: Response<List<Carta>>
                ) {
                    if (response.isSuccessful) {
                        val risultato: List<Carta>? = response.body()
                        deferred.complete(risultato)
                    } else {
                        deferred.complete(null)
                    }
                }

                override fun onFailure(call: Call<List<Carta>>, t: Throwable) {
                    deferred.complete(null)
                }
            })

            deferred.await()
        }
    }

    suspend fun saveCarta(creaCarta: CreaCarta): Carta? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Carta?>()
            ApiCarta.cartaService.saveCarta(creaCarta).enqueue(object :
                Callback<Carta?> {
                override fun onResponse(
                    call: Call<Carta?>,
                    response: Response<Carta?>
                ) {
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        if (risultato != null) {
                            deferred.complete(risultato)
                        }
                    }
                }
                override fun onFailure(call: Call<Carta?>, t: Throwable) {
                    deferred.complete(null)
                }
            })
            deferred.await()
        }
    }

    suspend fun deleteCarta(idCarta: UUID): Boolean {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Boolean>()

            ApiCarta.cartaService.deleteCarta(idCarta).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        deferred.complete(true)
                    } else {
                        deferred.complete(false)
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    deferred.complete(false)
                }
            })

            deferred.await()
        }
    }
}