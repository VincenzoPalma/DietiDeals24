package com.example.dietideals_app.viewmodel.listener

import com.example.dietideals_app.model.Asta

interface AsteListener {
    fun onAsteLoaded(aste: List<Asta>)
    fun onError()
}