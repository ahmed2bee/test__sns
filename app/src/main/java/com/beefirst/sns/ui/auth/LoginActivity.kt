package com.beefirst.sns.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.beefirst.sns.R
import com.beefirst.sns.enums.Consts
import com.beefirst.sns.ui.home.MainActivity
import com.beefirst.sns.utils.UserPreferences
import com.beefirst.sns.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Testing Only
        et_username.setText("aaa@hotmail.com")
        //      et_password.setText("Mahmoud1234")

        initViews()
        initObservables()

//        tvSaedEmployee.setOnClickListener { updateUIForSaedEmployee() }
//        tvCustomerEmployee.setOnClickListener { updateUIForCustomerEmployee() }
//
//        tvRegister.setOnClickListener {
//            startActivity(
//                Intent(
//                    this,
//                    RegistrationActivity::class.java
//                )
//            )
//            finish()
//        }
//        ivRegister.setOnClickListener {
//            startActivity(
//                Intent(
//                    this,
//                    RegistrationActivity::class.java
//                )
//            )
//            finish()
//        }
    }

    private fun initViews() {
        btnLogin.setOnClickListener {
            validate()
        }
        forgotPassword.setOnClickListener {
            navigateToForgetPassword()
        }

        signUp.setOnClickListener {
            navigateToRegistration()
        }
    }

    private fun validate() {
        if (et_username.text.isNullOrEmpty()) {
            et_username.error = "Email required"
            return
        }

        if (!isEmailValid(et_username.text.toString())) {
            et_username.error =  "invalid Email"
            return
        }

        hideKeyboard(this)
        loading(pbLoader, btnLogin)
        email = et_username.text.toString()
        loginViewModel.login(et_username.text.toString())
    }

    private fun initObservables() {
        loginViewModel.loginData.observe(this, {
            loading(pbLoader, btnLogin)
            val intent = Intent(this, VerificationOTPActivity::class.java);
            intent.putExtra(Consts.NAME, email)
            startActivity(intent)
            finish()
        })
    }

    private fun navigateToForgetPassword() {
        startActivity(
            Intent(
                this,
                ForgetPasswordActivity::class.java
            )
        )
    }

    private fun navigateToRegistration() {
        startActivity(
            Intent(
                this,
                RegistrationActivity::class.java
            )
        )
    }
}










