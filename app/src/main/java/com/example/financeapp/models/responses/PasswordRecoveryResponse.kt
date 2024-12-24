package com.example.financeapp.models.responses

data class PasswordRecoveryResponse(
    val password: String,
    val token: String
)
