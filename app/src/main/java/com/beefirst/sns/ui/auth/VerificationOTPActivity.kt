package com.beefirst.sns.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.beefirst.sns.R
import com.beefirst.sns.enums.Consts
import com.beefirst.sns.ui.home.MainActivity
import com.beefirst.sns.utils.UserPreferences
import com.beefirst.sns.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.pbLoader
import kotlinx.android.synthetic.main.activity_verification_otpactivity.*
import javax.inject.Inject

@AndroidEntryPoint
class VerificationOTPActivity : BaseActivity() {

    @Inject
    lateinit var userPreferences: UserPreferences

    private val verifyOTPViewModel: VerificationOTPViewModel by viewModels()
    var otp = ""
    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_otpactivity)

        initView()
        initObservables()
    }

    private fun initView() {
        email = intent.getStringExtra(Consts.NAME)!!
//        et_otp.setText("0000")
        btn_verify.setOnClickListener { validate() }
    }

    private fun validate() {
        if (et_otp.text.isNullOrEmpty()) {
            et_otp.error = "OTP required"
            return
        }

        if (et_otp.text.length != 4) {
            et_otp.error = "Invaled OTP"
            return
        }

        hideKeyboard(this)
        loading(pbLoader, btn_verify)
        otp = et_otp.text.toString()
        verifyOTPViewModel.verifyOTP(email, otp)
    }

    private fun initObservables() {
        verifyOTPViewModel.verifyOTP.observe(this, {
            if (it != null) {
                userPreferences.setLoggedInUser(it)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }
}