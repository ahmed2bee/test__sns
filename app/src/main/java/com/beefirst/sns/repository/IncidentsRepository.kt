package com.beefirst.sns.repository

import android.util.Log
import com.beefirst.sns.model.IncidentModel
import com.beefirst.sns.model.LoginResponse
import com.beefirst.sns.model.VerificationOTPModel
import com.beefirst.sns.model.requests.VerifyOTPRequest
import com.beefirst.sns.network.RetrofitApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IncidentsRepository @Inject constructor(
    private val retrofit: RetrofitApi
) {
    suspend fun getIncidents(
    ): IncidentModel? {

        return try{
            val response = retrofit.getIncident()
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