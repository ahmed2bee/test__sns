package com.beefirst.sns.model.requests

data class VerifyOTPRequest(
    val email: String,
    val otp: String
)