package com.example.dietideals_app.repository

import android.util.Log
import com.example.dietideals_app.model.Asta
import com.example.dietideals_app.model.enum.CategoriaAsta
import com.example.dietideals_app.model.enum.TipoAsta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AstaRepository {

    fun getAste(nome: String?, categoriaAsta: CategoriaAsta?, tipoAsta: TipoAsta?) {
        val TAG = "CHECK_RESPONSE"
        ApiAsta.astaService.getAste(0, 12, nome, tipoAsta, categoriaAsta).enqueue(object : Callback<List<Asta>>{
            override fun onResponse(
                call: Call<List<Asta>>,
                response: Response<List<Asta>>) {
                if (response.isSuccessful){
                    response.body()?.let { 
                        for (asta in it){
                            Log.i(TAG, "onResponse: ${asta.nome}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Asta>>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }

        })
    }
}