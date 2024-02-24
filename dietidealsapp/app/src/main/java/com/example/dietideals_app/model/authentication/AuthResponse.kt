package com.example.dietideals_app.model.authentication

data class AuthResponse(
    val kind: String?,
    val localId: String?,
    val email: String?,
    val displayName: String?,
    val idToken: String?,
    val registered: Boolean?,
    val refreshToken: String?,
    val expiresIn: String?
)