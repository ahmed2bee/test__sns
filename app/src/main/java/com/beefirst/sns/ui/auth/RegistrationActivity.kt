package com.beefirst.sns.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.beefirst.sns.R
import com.beefirst.sns.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_registration.*

@AndroidEntryPoint
class RegistrationActivity : BaseActivity() {

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initObservables()

        initView()
    }

    private fun initView() {
        registerBtn.setOnClickListener {
            validation(
                emailAddressEt.text.toString(),
                usernameEt.text.toString(),
                passwordEt.text.toString(),
                password2Et.text.toString())
        }
    }

    private fun validation(
        email: String,
        username: String,
        password: String,
        confirmPassword: String,
    ) {
        if (email.isEmpty()) {
            emailAddressEt.error = "برجاء ادخال بريد الكتروني صحيح"
            return
        }
        if (username.isEmpty()) {
            usernameEt.error = "برجاءادحال اسم المستحدم"
            return
        }
        if (password.isEmpty()) {
            passwordEt.error = "برجاءادخال كلمةالمرور"
            return
        }
        if (confirmPassword.isEmpty()) {
            password2Et.error = "برجاءادخال كلمةالمرور"
            return
        }
        registration(
            email, username, password, confirmPassword
        )
    }

    private fun registration(
        email: String,
        username: String,
        password: String,
        confirmPassword: String,
    ) {
        viewModel.register(email, username, password, confirmPassword)
    }

    private fun initObservables() {
        viewModel.registration.observe(this, {
            if (it == null || it.message == "Email Already Exist") {
                emailAddressEt.error = "هذا الايميل مسجل بالفعل"
                return@observe
            } else
                Toast.makeText(baseContext, "تم التسجيل بنجاح", Toast.LENGTH_LONG)
                    .show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })
    }

}
















