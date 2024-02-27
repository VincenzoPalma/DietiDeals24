package com.example.dietideals_app.repository

import com.example.dietideals_app.model.Utente
import com.example.dietideals_app.model.dto.DatiProfiloUtente
import com.example.dietideals_app.model.dto.UtenteRegistrazione
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.Buffer
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
                    }
                }

                override fun onFailure(call: Call<DatiProfiloUtente>, t: Throwable) {
                    deferred.complete(null)
                }
            })
            deferred.await()
        }
    }

    suspend fun createUtente(datiRegistrazione: UtenteRegistrazione): Utente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Utente?>()
            RetrofitClient.ApiRegistrazione.utenteService.createUtente(datiRegistrazione).enqueue(object :
                Callback<Utente> {
                override fun onResponse(
                    call: Call<Utente>,
                    response: Response<Utente>
                ) {
                    val requestBody = call.request().body()
                    val buffer = Buffer()
                    requestBody?.writeTo(buffer)
                    val requestBodyString = buffer.readUtf8()
                    println("Request body: $requestBodyString")
                    println(response.code())
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        println(risultato)
                        deferred.complete(risultato)
                    } else {
                        deferred.complete(null)
                    }
                }
                override fun onFailure(call: Call<Utente>, t: Throwable) {
                    println(t.message)
                    deferred.complete(null)
                }
            })
            deferred.await()
        }
    }

    suspend fun getUtenteByEmail(email: String): Utente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Utente?>()
            RetrofitClient.ApiRegistrazione.utenteService.getUtenteByEmail(email).enqueue(object :
                Callback<Utente> {
                override fun onResponse(
                    call: Call<Utente>,
                    response: Response<Utente>
                ) {
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        deferred.complete(risultato)
                    }
                }

                override fun onFailure(call: Call<Utente>, t: Throwable) {
                    deferred.complete(null)
                }
            })
            deferred.await()
        }
    }

    suspend fun getUtenteByUsername(username: String): Utente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Utente?>()
            RetrofitClient.ApiRegistrazione.utenteService.getUtenteByUsername(username).enqueue(object :
                Callback<Utente> {
                override fun onResponse(
                    call: Call<Utente>,
                    response: Response<Utente>
                ) {
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        deferred.complete(risultato)
                    }
                }

                override fun onFailure(call: Call<Utente>, t: Throwable) {
                    deferred.complete(null)
                }
            })
            deferred.await()
        }
    }

}