package com.example.dietideals_app.model

import java.util.Currency

class Offerta (val prezzo : Currency){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Offerta

        return prezzo == other.prezzo
    }

    override fun hashCode(): Int {
        return prezzo.hashCode()
    }
}
