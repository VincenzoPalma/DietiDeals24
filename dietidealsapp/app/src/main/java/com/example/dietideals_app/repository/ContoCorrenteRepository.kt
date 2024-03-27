package com.example.dietideals_app.repository

import com.example.dietideals_app.model.ContoCorrente
import com.example.dietideals_app.model.dto.CreaContoCorrente
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContoCorrenteRepository {

    suspend fun saveContoCorrente(creaContoCorrente: CreaContoCorrente): ContoCorrente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<ContoCorrente?>()
            ApiContoCorrente.contoCorrenteService.saveContoCorrente(creaContoCorrente)
                .enqueue(object :
                    Callback<ContoCorrente?> {
                    override fun onResponse(
                        call: Call<ContoCorrente?>,
                        response: Response<ContoCorrente?>
                    ) {
                        if (response.isSuccessful) {
                            val risultato = response.body()
                            if (risultato != null) {
                                deferred.complete(risultato)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ContoCorrente?>, t: Throwable) {
                        deferred.complete(null)
                    }
                })
            deferred.await()
        }
    }

    suspend fun modifyContoCorrente(contoCorrente: ContoCorrente): ContoCorrente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<ContoCorrente?>()
            ApiContoCorrente.contoCorrenteService.modifyContoCorrente(contoCorrente)
                .enqueue(object :
                    Callback<ContoCorrente?> {
                    override fun onResponse(
                        call: Call<ContoCorrente?>,
                        response: Response<ContoCorrente?>
                    ) {
                        if (response.isSuccessful) {
                            val risultato = response.body()
                            if (risultato != null) {
                                deferred.complete(risultato)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ContoCorrente?>, t: Throwable) {
                        deferred.complete(null)
                    }
                })
            deferred.await()
        }
    }

    suspend fun getContoCorrente(): ContoCorrente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<ContoCorrente?>()
            ApiContoCorrente.contoCorrenteService.getContoCorrente().enqueue(object :
                Callback<ContoCorrente?> {
                override fun onResponse(
                    call: Call<ContoCorrente?>,
                    response: Response<ContoCorrente?>
                ) {
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        if (risultato != null) {
                            deferred.complete(risultato)
                        }
                    }
                }

                override fun onFailure(call: Call<ContoCorrente?>, t: Throwable) {
                    deferred.complete(null)
                }
            })
            deferred.await()
        }
    }

}