package com.example.dietideals_app.viewmodel.listener

import com.example.dietideals_app.model.ContoCorrente

interface ContoCorrenteListener {

    fun onContoCorrenteLoaded(contoCorrenteUtente: ContoCorrente)

    fun onError()
}