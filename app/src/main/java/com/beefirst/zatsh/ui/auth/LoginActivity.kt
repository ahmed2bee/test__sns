package com.beefirst.zatsh.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.beefirst.zatsh.R
import com.beefirst.zatsh.ui.home.MainActivity
import com.beefirst.zatsh.utils.UserPreferences
import com.beefirst.zatsh.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    @Inject
    lateinit var userPreferences: UserPreferences

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Testing Only
//        et_username.setText("mydro6@hotmail.com")
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

//
//        tv_selectLanguage.setOnClickListener {
//            hideView(login_linearLayout)
//            showView(language_layout)
//        }
    }

    private fun validate() {
        if (et_username.text.isNullOrEmpty()) {
            et_username.error = "برجاء ادخال البريد الالكتدوني"
            return
        }
        if (et_password.text.isNullOrEmpty()) {
            et_password.error = "برجاء ادخال كلمه السر"
            return
        }
        hideKeyboard(this)
        loading(pbLoader, btnLogin)
        login(et_username.text.toString(), et_password.text.toString())
    }

    private fun login(email: String, password: String) {
        loginViewModel.login(email, password)
    }

    private fun initObservables() {
        loginViewModel.loginData.observe(this, {
            loading(pbLoader, btnLogin)
            if (it == null)
                showMessage(resources.getString(R.string.please_check_internet))
            else {
                if (!it.status)
                    showMessage("Wrong email or password")
                else {
                    userPreferences.setLoggedInUser(it.data)
                    userPreferences.setToken(it.data.token)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
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










