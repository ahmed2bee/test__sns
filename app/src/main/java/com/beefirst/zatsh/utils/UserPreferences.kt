package com.beefirst.zatsh.utils

import android.content.Context
import android.content.SharedPreferences
import com.beefirst.zatsh.BaseApplication
import com.beefirst.zatsh.model.User
import com.google.gson.Gson
import javax.inject.Singleton

@Singleton
class UserPreferences {

    private var sharePreferences: SharedPreferences =
        BaseApplication.context!!.getSharedPreferences("saved_token", Context.MODE_PRIVATE)

    private var sharePreferencesCode: SharedPreferences =
        BaseApplication.context!!.getSharedPreferences("saved_code", Context.MODE_PRIVATE)

    fun getLoggedInUser(): User {
        val userData: String = sharePreferences.getString("KEY_USER_DATA", "")!!
        return Gson().fromJson(userData, User::class.java)
    }

    fun setLoggedInUser(user: User) {
        sharePreferences.edit().putString("KEY_USER_DATA", Gson().toJson(user)).apply()
    }

    fun setToken(token: String) {
        sharePreferences.edit().putString("token", token).apply()
    }

    fun getToken(): String {
        return "Token ${sharePreferences.getString("token", "")!!}"
    }

    fun saveUserImg(imgUrl: String) {
        sharePreferences.edit().putString("imgUrl", imgUrl).apply()
    }

    fun getUserImg(): String {
        return sharePreferences.getString("imgUrl", "")!!
    }

    fun saveShippingFees(fees: String) {
        sharePreferences.edit().putString("fees", fees).apply()
    }

    fun getShippingFees(): String {
        return sharePreferences.getString("fees", "nullx")!!
    }

    fun clearToken() {
        sharePreferences.edit().clear().apply()
    }

    fun setLat(lat: String) = sharePreferences.edit().putString("lat", lat).apply()
    fun setLng(lng: String) = sharePreferences.edit().putString("lng", lng).apply()

    fun clearLat() {
        sharePreferences.edit().clear().apply()
    }

    fun clearLng() {
        sharePreferences.edit().clear().apply()
    }

    fun setAddress(address: String) = sharePreferences.edit().putString("address", address).apply()

    fun getAddress(): String {
        return sharePreferences.getString("address", "nullx")!!
    }

    fun setMobile(mobile: String) = sharePreferences.edit().putString("mobile", mobile).apply()

    fun getMobile(): String {
        return sharePreferences.getString("mobile", "nullx")!!
    }

    fun setAddressTemp(address: String) =
        sharePreferences.edit().putString("addressTemp", address).apply()

    fun getAddressTemp(): String {
        return sharePreferences.getString("addressTemp", "nullx")!!
    }

    fun setMobileTemp(mobile: String) =
        sharePreferences.edit().putString("mobileTemp", mobile).apply()

    fun getMobileTemp(): String {
        return sharePreferences.getString("mobileTemp", "nullx")!!
    }

    fun setName(name: String) =
        sharePreferences.edit().putString("fname", name).apply()

    fun getName(): String {
        return sharePreferences.getString("fname", "nullx")!!
    }

    fun getLat(): String {
        return sharePreferences.getString("lat", "null")!!
    }

    fun getLng(): String {
        return sharePreferences.getString("lng", "null")!!
    }

    fun saveProductCode(code: String) {
        sharePreferencesCode.edit().putString("code", code).apply()
    }

    fun getProductCode(): String {
        return sharePreferencesCode.getString("code", "nullx")!!
    }

//    fun isAnonymous(): Boolean {
//        if (getToken().equals("Token nullx")) return true
//        return false
//    }

    fun isAnonymous(): Boolean {
        if (getToken().equals("Token nullx")) return true
        return false
    }

    fun setAnonymous(isAnonymous: Boolean): Boolean {
        return isAnonymous
    }

//    fun checkLoginDialog(activity: Activity) {
//        AlertDialog.Builder(activity)
//            .setTitle("تسجيل دخول")
//            .setMessage("يجب تسجيل الدخول قبل اتمام عمليه الشراء")
//            .setPositiveButton(
//                "تسجيل دخول"
//            ) { _, _ ->
//                activity.startActivity(Intent(activity, LoginActivity::class.java))
//            }
//            .setNegativeButton("لاحقاً", null)
//            .setIcon(android.R.drawable.ic_dialog_alert)
//            .show()
//    }
}