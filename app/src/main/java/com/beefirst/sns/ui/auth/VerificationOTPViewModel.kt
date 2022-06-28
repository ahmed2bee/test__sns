package com.beefirst.sns.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beefirst.sns.model.LoginResponse
import com.beefirst.sns.model.VerificationOTPModel
import com.beefirst.sns.repository.LoginRepository
import com.beefirst.sns.repository.VerificationOTPRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationOTPViewModel @Inject constructor(
    private val repository: VerificationOTPRepository,
) : ViewModel() {

    private val _verifyOTP: MutableLiveData<VerificationOTPModel> by lazy { MutableLiveData() }
    val verifyOTP: LiveData<VerificationOTPModel> get() = _verifyOTP

    fun verifyOTP(email: String, otp: String) {
        viewModelScope.launch {
            _verifyOTP.postValue(repository.verifyOTP(email, otp))
        }
    }
}