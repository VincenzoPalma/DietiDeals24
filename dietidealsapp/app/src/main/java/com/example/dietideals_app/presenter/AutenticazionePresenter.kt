package com.example.dietideals_app.presenter

class AutenticazionePresenter {
    fun effettuaLogin(username: String, password: String) {


    }

    fun effettuaRegistrazione() {

    }
    fun controlloPassword(password : String) : Boolean
    {
        return password.length >= 5
    }
}