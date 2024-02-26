package com.example.dietideals_app.viewmodel

import android.annotation.SuppressLint
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.dietideals_app.model.dto.UtenteRegistrazione
import com.example.dietideals_app.repository.UtenteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

class PaginaRegistrazioneViewModel {

    private val utenteRepository = UtenteRepository()

    fun isEmailValid(email: String): Boolean {
        // In questo esempio, si utilizza un'espressione regolare per verificare il formato dell'email
        val emailRegex = Regex("^[A-Za-z](.*)(@)(.+)(\\.)(.+)")
        return email.matches(emailRegex)
    }

    // Funzione di validazione dell'username
    fun isUsernameValid(username: String): Boolean {
        // Puoi definire i criteri di validazione dell'username qui
        return username.isNotBlank()
    }

    // Funzione di validazione della password
    fun isPasswordValid(password: String): Boolean {
        // Puoi definire i criteri di validazione della password qui (es. lunghezza minima)
        return password.length >= 8
    }

    // Funzione di verifica della corrispondenza tra password e conferma password
    fun isPasswordMatching(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    // Funzione di validazione del nome
    fun isNomeValid(nome: String): Boolean {
        // Puoi definire i criteri di validazione del nome qui
        return nome.isNotBlank()
    }

    // Funzione di validazione del cognome
    fun isCognomeValid(cognome: String): Boolean {
        // Puoi definire i criteri di validazione del cognome qui
        return cognome.isNotBlank()
    }

    private fun calculateAge(birthDate: Date, currentDate: Date): Int {
        val birthCalendar = Calendar.getInstance()
        val currentCalendar = Calendar.getInstance()

        birthCalendar.time = birthDate
        currentCalendar.time = currentDate

        var age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

        // Riduci l'età di uno se la data di nascita non è ancora arrivata quest'anno
        if (currentCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age
    }

    fun isUserAdult(selectedDateMillis: Long?): Boolean {
        // Verifica se la data di nascita è stata selezionata
        if (selectedDateMillis == null) {
            return false
        }

        // Calcola la data corrente
        val currentDate = Calendar.getInstance().time

        // Ottieni la data di nascita dall'input dell'utente
        val birthDate = Date(selectedDateMillis)

        // Calcola l'età dell'utente
        val age = calculateAge(birthDate, currentDate)

        // Verifica se l'utente è maggiorenne (ad esempio, se ha almeno 18 anni)
        return age >= 18
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun checkFields(email : String, password: String, confermaPassword: String, nome: String, cognome: String, username: String, state: DatePickerState): Boolean {
        // Verifica qui tutti i campi e restituisci true solo se sono tutti compilati correttamente
        return isEmailValid(email) && isUsernameValid(username) && isPasswordValid(
            password
        ) && isPasswordMatching(
            password, confermaPassword
        ) && isNomeValid(nome) && isCognomeValid(cognome) && isUserAdult(state.selectedDateMillis)
    }

    fun isValidPartitaIva(partitaIva: String): Boolean {
        return partitaIva.length == 11 && partitaIva.all { it.isDigit() }
    }

    fun isValidNomeTitolare(nomeTitolare: String): Boolean {
        return nomeTitolare.isNotBlank()
    }

    fun isValidCodiceBicSwift(codiceBicSwift: String): Boolean {
        return codiceBicSwift.length == 8 || codiceBicSwift.length == 11
    }

    fun isValidIban(iban: String): Boolean {
        return iban.length == 27 && iban.matches(Regex("[A-Za-z0-9]+"))
    }

    fun checkFieldsDatiVenditore(nomeTitolare: String, codiceBicSwift: String, partitaIva: String, iban: String): Boolean {
        return isValidNomeTitolare(nomeTitolare) && isValidCodiceBicSwift(codiceBicSwift) && isValidIban(iban) && isValidPartitaIva(partitaIva)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertMillisToDate(millis: Long): LocalDate {
        val formatterMillisToString = SimpleDateFormat("yyyy-MM-dd")
        val dateString = formatterMillisToString.format(Date(millis))
        val formatterStringToLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(dateString, formatterStringToLocalDate)
    }

    suspend fun registraUtente(datiRegistrazione: UtenteRegistrazione) {
        withContext(Dispatchers.IO) {
            utenteRepository.createUtente(datiRegistrazione)
        }
    }
}