package com.beefirst.sns.model

data class LoginResponse(
    val roles: List<Int>,
    val token: String
)