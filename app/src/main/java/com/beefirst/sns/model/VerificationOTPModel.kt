package com.beefirst.sns.model

data class VerificationOTPModel(
    val roles: List<Int>,
    val token: String
)