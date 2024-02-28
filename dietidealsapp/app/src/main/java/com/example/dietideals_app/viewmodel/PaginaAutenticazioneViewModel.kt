package com.example.dietideals_app.viewmodel

import com.example.dietideals_app.repository.UtenteRepository
import java.util.regex.Pattern

class PaginaAutenticazioneViewModel {
    val repository = UtenteRepository()
    val viewModelRegistrazione = PaginaRegistrazioneViewModel()

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.com|it)\$"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

}