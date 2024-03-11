package com.example.dietideals_app.viewmodel.listener

import com.example.dietideals_app.model.Carta
import com.example.dietideals_app.model.ContoCorrente

interface ContoCorrenteListener {

    fun onContoCorrenteLoaded(contoCorrente: ContoCorrente)

    fun onContoCorrenteSaved(contoCorrente: ContoCorrente)

    fun onError()
}