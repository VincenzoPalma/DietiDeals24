package com.example.dietideals_app.model.dto

import com.example.dietideals_app.model.ContoCorrente
import com.example.dietideals_app.model.enum.RuoloUtente

class UtenteRegistrazione(
    val username: String,
    val ruolo: RuoloUtente,
    val nome: String,
    val cognome: String,
    var dataNascita: String,
    val email: String,
    val password: String?,
    val partitaIva: String?,
    val urlDocumentoIdentita: String?,
    val urlFotoProfilo: String?,
    val contoCorrente: ContoCorrente?
) {
}