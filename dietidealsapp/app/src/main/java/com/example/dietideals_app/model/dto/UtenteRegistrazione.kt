package com.example.dietideals_app.model.dto

import com.example.dietideals_app.model.ContoCorrente
import com.example.dietideals_app.model.enum.RuoloUtente
import java.time.LocalDate

class UtenteRegistrazione(
    val username: String,
    val ruoloUtente: RuoloUtente,
    val nome: String,
    val cognome: String,
    var dataNascita: String,
    val email: String,
    val password: String,
    val partitaIva: String?,
    val urlDocumentoIdentita: String?,
    val urlFotoProfilo: String?,
    val contoCorrente: ContoCorrente?
) {
}