package com.beefirst.sns.model

data class RegistrationResponse(
    val `data`: RegisterData,
    val message: String,
    val status: Boolean,
)

data class RegisterData(
    val email: String,
    val pk: Int,
    val token: String,
    val username: String,
)