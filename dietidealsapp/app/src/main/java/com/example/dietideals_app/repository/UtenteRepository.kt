package com.example.dietideals_app.repository

import com.example.dietideals_app.model.authentication.AuthResponse
import com.example.dietideals_app.model.dto.DatiProfiloUtente
import com.example.dietideals_app.model.authentication.LoginInfo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLDecoder
import java.net.URLEncoder
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

    /*suspend fun loginUtente(email: String, password: String) : String? {
        val datiLogin = LoginInfo(email, password, true)
        println("1")
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<String?>()
            println("2")
            ApiLogin.utenteService.loginUtente(URLEncoder.encode("https://identitytoolkit.googleapis.com/v1/accounts:AsignInWithPassword","UTF-8"),"AIzaSyDkyp8YvQL8TQltifYi2rR-5hP0IGTUeGc", datiLogin).enqueue(object :
                Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    println(call.request().url())
                    println("3")
                    println(response.code())
                    if (response.isSuccessful) {
                        val risultato = response.body()
                        deferred.complete(risultato?.idToken)
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    println("4")
                    println(t.message)
                    deferred.complete(null)
                }
            })
            deferred.await()
        }
    } */
}