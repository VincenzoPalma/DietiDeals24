package com.example.dietideals_app.viewmodel

import com.example.dietideals_app.model.enum.CategoriaAsta
import com.example.dietideals_app.model.enum.TipoAsta
import com.example.dietideals_app.repository.AstaRepository
import com.example.dietideals_app.repository.NotificaRepository
import com.example.dietideals_app.viewmodel.listener.AsteListener
import com.example.dietideals_app.viewmodel.listener.NotificaListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SchermataHomeViewModel {

    private val astaRepostory = AstaRepository()
    private val notificaRepostory = NotificaRepository()

    fun mostraAste(
        numeroPagina: Int,
        nome: String?,
        categoriaAsta: CategoriaAsta?,
        tipoAsta: TipoAsta?,
        listener: AsteListener
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val aste = astaRepostory.getAste(numeroPagina, nome, categoriaAsta, tipoAsta)
            if (aste != null) {
                listener.onAsteLoaded(aste)
            } else {
                listener.onError()
            }
        }
    }

    fun mostraNotifiche(listener: NotificaListener) {
        CoroutineScope(Dispatchers.IO).launch {
            val notifiche = notificaRepostory.getNotifiche()
            if (notifiche != null) {
                listener.onNotificheLoaded(notifiche)
            } else {
                listener.onError()
            }
        }
    }

    fun deleteNotifiche(listener: NotificaListener) {
        CoroutineScope(Dispatchers.IO).launch {
            val eliminate = notificaRepostory.deleteNotifiche()
            if (eliminate) {
                listener.onNotificheDeleted()
            } else {
                listener.onError()
            }
        }
    }
}