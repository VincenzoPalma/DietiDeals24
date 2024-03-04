package com.example.dietideals_app.viewmodel.listener

import com.example.dietideals_app.model.Notifica

interface NotificaListener {

    fun onNotificheLoaded(notifiche: List<Notifica>)

    fun onNotificheDeleted()
    fun onError()
}