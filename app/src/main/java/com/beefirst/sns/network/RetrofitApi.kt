package com.beefirst.sns.network

import com.beefirst.sns.model.*
import com.beefirst.sns.model.requests.VerifyOTPRequest
import retrofit2.Response
import retrofit2.http.*

interface RetrofitApi {

    @FormUrlEncoded
    @POST("login/")
    suspend fun login(
        @Field("email") email: String,
    ): Response<String>

    @POST("verify-otp/")
    suspend fun verifyOtp(
        @Body request: VerifyOTPRequest
    ): Response<VerificationOTPModel>

    @GET("incident")
    suspend fun getIncident(
    ): Response<IncidentModel>

    @FormUrlEncoded
    @POST("password_reset/")
    suspend fun sendEmailResetPassword(
        @Field("email") email: String,
    ): Response<ForgetPasswordResponse>

    @FormUrlEncoded
    @POST("password_reset/confirm/")
    suspend fun resetPasswordConfirm(
        @Field("token") code: String,
        @Field("password") password: String,
    ): Response<ForgetPasswordResponse>

    @FormUrlEncoded
    @POST("password_reset/validate_token/")
    suspend fun emailCodeValidation(
        @Field("token") code: String,
    ): Response<ForgetPasswordResponse>

    @FormUrlEncoded
    @POST("account/register/")
    suspend fun register(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("password2") password2: String,
    ): Response<RegistrationResponse>
}