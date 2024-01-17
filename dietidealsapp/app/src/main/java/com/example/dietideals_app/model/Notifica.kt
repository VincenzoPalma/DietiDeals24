package com.example.dietideals_app.model

import java.time.LocalDateTime

class Notifica(val data : LocalDateTime) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Notifica

        return data == other.data
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }
}
