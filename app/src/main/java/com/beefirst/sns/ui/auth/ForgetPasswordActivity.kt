package com.beefirst.sns.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.beefirst.sns.R
import com.beefirst.sns.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_forget_password.*

@AndroidEntryPoint
class ForgetPasswordActivity : BaseActivity() {
    private val viewModel: ForgetPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        initObservables()
        initViews()

    }

    private fun initViews() {
        sendEmailBtn.setOnClickListener {
            sendCode(emailAddressEt.text.toString())
        }
        resetBtn.setOnClickListener {
            codeValidation(emailAddressEt.text.toString())
        }
    }

    private fun initObservables() {
        viewModel.codeConfirm.observe(this, {
            if (it != null || it?.status == "OK") {
                resetPassword(
                    emailAddressEt.text.toString(),
                    passwordEt.text.toString())
            } else
                emailAddressEt.error = "كود خاطئ"
            return@observe
        })
        viewModel.sendCode.observe(this, {
            if (it != null || it?.status == "OK")
                updateUI()
            else
                emailAddressEt.error = "يرجي ادخال بريد الكتروني صحيح"
        })

        viewModel.resetConfirm.observe(this, {
            Toast.makeText(baseContext, "تم اعادة تعيين كلمة المرور بنجاح", Toast.LENGTH_LONG)
                .show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })
    }

    private fun sendCode(email: String) {
        if (email.isEmpty()) {
            emailAddressEt.error = "برجاء ادخال البريد الالكتروني"
            return
        } else
            viewModel.sendCode(email)
    }

    private fun codeValidation(code: String) {
        viewModel.codeValidation(code)
    }


    private fun resetPassword(code: String, password: String) {
        if (code.isEmpty()) {
            emailAddressEt.error = "برجاء ادخال الكود"
            return
        }
        if (password.isEmpty()) {
            passwordEt.error = "يرجي ادخال كلمة المرور"
            return
        }

        if (password2Et.text.isNullOrEmpty()) {
            password2Et.error = "يرجي ادخال كلمة المرور"
            return
        }
        if (password.length < 8) {
            passwordEt.error = "كلمة المرور أقل من 8 أرقام"
            return
        }
        if (password != password2Et.text.toString()) {
            password2Et.error = "كلمة المرور غير متطابقة"
        }
        viewModel.resetPassword(code, password)
    }

    private fun updateUI() {
        sendEmailBtn.visibility = View.GONE
        emailAddressEt.text.clear()
        emailAddressEt.hint = "Code"
        emailAddressEt.inputType = InputType.TYPE_CLASS_NUMBER
        resetBtn.visibility = View.VISIBLE
        passwordEt.visibility = View.VISIBLE
        password2Et.visibility = View.VISIBLE
    }
}