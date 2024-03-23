package com.example.dietideals_app.repository

import com.example.dietideals_app.model.Asta
import com.example.dietideals_app.model.dto.CreaAsta
import com.example.dietideals_app.model.enum.CategoriaAsta
import com.example.dietideals_app.model.enum.StatoAsta
import com.example.dietideals_app.model.enum.TipoAsta
import com.example.dietideals_app.model.list.AstaList
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AstaRepository {

    suspend fun getAste(numeroPagina : Int, nome: String?, categoria: CategoriaAsta?, tipo: TipoAsta?): List<Asta>? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<List<Asta>?>()

            ApiAsta.astaService.getAste(numeroPagina, 12, nome, tipo, categoria).enqueue(object :
                Callback<AstaList> {
                override fun onResponse(
                    call: Call<AstaList>,
                    response: Response<AstaList>
                ) {
                    if (response.isSuccessful) {
                        val risultato: List<Asta>? = response.body()?.content
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

    suspend fun getAsteUtente(numeroPagina : Int, stato: StatoAsta?): List<Asta>? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<List<Asta>?>()

            ApiAsta.astaService.getAsteUtente(numeroPagina, 12, stato).enqueue(object :
                Callback<AstaList> {
                    override fun onResponse(
                    call: Call<AstaList>,
                    response: Response<AstaList>
                ) {
                    if (response.isSuccessful) {
                        val risultato: List<Asta>? = response.body()?.content
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

    suspend fun getAstePartecipateUtente(numeroPagina : Int, vinta: Boolean): List<Asta>? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<List<Asta>?>()

            ApiAsta.astaService.getAstePartecipate(numeroPagina, 12, vinta).enqueue(object :
                Callback<AstaList> {
                override fun onResponse(
                    call: Call<AstaList>,
                    response: Response<AstaList>
                ) {
                    if (response.isSuccessful) {
                        val risultato: List<Asta>? = response.body()?.content
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

    suspend fun saveAsta(creaAsta: CreaAsta): Asta? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Asta?>()

            ApiAsta.astaService.saveAsta(creaAsta).enqueue(object :
                Callback<Asta> {
                override fun onResponse(
                    call: Call<Asta>,
                    response: Response<Asta>
                ) {
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        deferred.complete(risultato)
                    } else {
                        println(response.message())
                        deferred.complete(null)
                    }
                }

                override fun onFailure(call: Call<Asta>, t: Throwable) {
                    println(t.message)
                    deferred.complete(null)
                }
            })

            deferred.await()
        }
    }

}