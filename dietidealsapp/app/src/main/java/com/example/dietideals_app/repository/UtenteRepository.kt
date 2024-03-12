package com.example.dietideals_app.repository

import com.example.dietideals_app.model.Utente
import com.example.dietideals_app.model.dto.DatiProfiloUtente
import com.example.dietideals_app.model.dto.UtenteRegistrazione
import com.example.dietideals_app.model.enum.RuoloUtente
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
            RetrofitClient.ApiRegistrazione.utenteService.createUtente(
                datiRegistrazione,
                idFirebase
            ).enqueue(object :
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

    suspend fun modificaDatiUtente(datiProfiloUtente: DatiProfiloUtente): Utente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Utente?>()

            ApiUtente.utenteService.modificaDatiUtente(datiProfiloUtente).enqueue(object :
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

    suspend fun getUtenteByIdAuth(idAuth: String): Utente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Utente?>()
            ApiUtente.utenteService.getUtenteByIdAuth(idAuth).enqueue(object :
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

    suspend fun getRuoloUtente(): RuoloUtente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<RuoloUtente?>()
            ApiUtente.utenteService.getRuoloUtente().enqueue(object :
                Callback<RuoloUtente> {
                override fun onResponse(
                    call: Call<RuoloUtente>,
                    response: Response<RuoloUtente>
                ) {
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        deferred.complete(risultato)
                    }
                }

                override fun onFailure(call: Call<RuoloUtente>, t: Throwable) {
                    deferred.complete(null)
                }
            })
            deferred.await()
        }
    }

    suspend fun modificaPartitaIva(partitaIva: String): Utente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Utente?>()
            ApiUtente.utenteService.modificaPartitaIva(partitaIva).enqueue(object :
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

    suspend fun modificaDocumentoVenditore(urlDocumento: String): Utente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Utente?>()
            ApiUtente.utenteService.modificaDocumentoVenditore(urlDocumento).enqueue(object :
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

    suspend fun getPartitaIva(): String? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<String?>()
            ApiUtente.utenteService.getPartitaIvaUtente().enqueue(object :
                Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        deferred.complete(risultato)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    deferred.complete(null)
                }
            })
            deferred.await()
        }
    }

    suspend fun setUtenteVenditore(): Utente? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Utente?>()
            ApiUtente.utenteService.setUtenteVenditore().enqueue(object :
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