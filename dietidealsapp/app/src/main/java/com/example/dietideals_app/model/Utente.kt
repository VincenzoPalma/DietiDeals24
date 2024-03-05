package com.example.dietideals_app.model

import com.example.dietideals_app.model.enum.RuoloUtente
import java.io.File
import java.time.LocalDate
import java.util.UUID

class Utente(
    val id: UUID,
    val username: String,
    val nome: String,
    val cognome: String,
    val dataDiNascita: LocalDate,
    var partitaIva: String,
    var documentoRiconoscimento: File,
    var immagineProfilo: File,
    var contoCorrente: ContoCorrente?,
    var carteAssociate: MutableSet<Carta> = mutableSetOf(),
    var aste: MutableSet<Asta> = mutableSetOf(),
    var offerte: MutableSet<Offerta> = mutableSetOf(),
    var notifiche: MutableSet<Notifica> = mutableSetOf(),
    var ruolo: RuoloUtente,
    var descrizione: String?,
    var sitoWeb: String?,
    var indirizzo: String?,
    var instagram: String?,
    var facebook: String?,
    var twitter: String?
)