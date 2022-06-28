package com.beefirst.sns.repository

import android.util.Log
import com.beefirst.sns.model.LoginResponse
import com.beefirst.sns.model.VerificationOTPModel
import com.beefirst.sns.model.requests.VerifyOTPRequest
import com.beefirst.sns.network.RetrofitApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerificationOTPRepository @Inject constructor(
    private val retrofit: RetrofitApi
) {
    suspend fun verifyOTP(
        email: String,
        otp: String
    ): VerificationOTPModel? {

        return try{
            val response = retrofit.verifyOtp(VerifyOTPRequest(email, otp))
            when (response.isSuccessful) {
                true -> response.body()
                false -> null
            }
        } catch (e: Exception) {
            Log.e("ErrorConnection", "${e.message} <<<")
            null
        }
    }
}