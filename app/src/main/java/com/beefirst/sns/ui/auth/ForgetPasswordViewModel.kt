package com.beefirst.sns.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beefirst.sns.model.ForgetPasswordResponse
import com.beefirst.sns.repository.ForgetPasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val repository: ForgetPasswordRepository,
) : ViewModel() {

    private val _sendCode: MutableLiveData<ForgetPasswordResponse> by lazy { MutableLiveData() }
    val sendCode: LiveData<ForgetPasswordResponse> get() = _sendCode

    private val _resetConfirm: MutableLiveData<ForgetPasswordResponse> by lazy { MutableLiveData() }
    val resetConfirm: LiveData<ForgetPasswordResponse> get() = _resetConfirm

    private val _codeConfirm: MutableLiveData<ForgetPasswordResponse> by lazy { MutableLiveData() }
    val codeConfirm: LiveData<ForgetPasswordResponse> get() = _codeConfirm

    fun sendCode(email: String) {
        viewModelScope.launch {
            _sendCode.postValue(repository.sendEmailForgetPassword(email))
        }
    }

    fun resetPassword(
        code: String,
        password: String,
    ) {
        viewModelScope.launch {
            _resetConfirm.postValue(repository.resetPassword(code, password))
        }
    }

    fun codeValidation(code: String) {
        viewModelScope.launch {
            _codeConfirm.postValue(repository.codeValidation(code))
        }
    }

}