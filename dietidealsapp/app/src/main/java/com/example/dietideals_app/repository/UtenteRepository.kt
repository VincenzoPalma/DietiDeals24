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

    suspend fun createUtente(datiRegistrazione: UtenteRegistrazione, idFirebase: String?): Utente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Utente?>()
            RetrofitClient.ApiRegistrazione.utenteService.createUtente(datiRegistrazione, idFirebase).enqueue(object :
                Callback<Utente> {
                override fun onResponse(
                    call: Call<Utente>,
                    response: Response<Utente>
                ) {
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        deferred.complete(risultato)
                    } else {
                        deferred.complete(null)
                    }
                }
                override fun onFailure(call: Call<Utente>, t: Throwable) {
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


}