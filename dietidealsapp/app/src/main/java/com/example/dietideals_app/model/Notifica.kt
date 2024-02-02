package com.example.dietideals_app.model

import java.time.LocalDateTime

class Notifica(val contenuto : String, val data : LocalDateTime) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Notifica

        if (contenuto != other.contenuto) return false
        return data == other.data
    }

    override fun hashCode(): Int {
        var result = contenuto.hashCode()
        result = 31 * result + data.hashCode()
        return result
    }

}
