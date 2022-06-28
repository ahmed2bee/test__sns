package com.beefirst.sns.repository

import android.util.Log
import com.beefirst.sns.model.LoginResponse
import com.beefirst.sns.network.RetrofitApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val retrofit: RetrofitApi
) {
    suspend fun login(
        email: String
    ): String? {

        return try{
            val response = retrofit.login(email)
            when (response.isSuccessful) {
                true -> {
                    response.body()
                }
                false -> {
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("ErrorConnection", "${e.message} <<<")
            null
        }
    }
}