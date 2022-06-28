package com.beefirst.sns.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beefirst.sns.model.LoginResponse
import com.beefirst.sns.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
) : ViewModel() {

    private val _loginData: MutableLiveData<String> by lazy { MutableLiveData() }
    val loginData: LiveData<String> get() = _loginData

    fun login(email: String) {
        viewModelScope.launch {
            _loginData.postValue(repository.login(email))
        }
    }
}