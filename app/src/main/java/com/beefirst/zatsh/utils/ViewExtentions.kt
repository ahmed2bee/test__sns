package com.beefirst.zatsh.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.beefirst.zatsh.R
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.INVISIBLE
}

fun Context.registerReceiver(
    intentFilter: IntentFilter,
    onReceive: (intent: Intent?) -> Unit
): BroadcastReceiver {
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            onReceive(intent)
        }
    }
    this.registerReceiver(receiver, intentFilter)
    return receiver
}

private fun showSnackBar(message: String, activity: Activity) {
    val snackBar = Snackbar
        .make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
    val sbView = snackBar.view
    val textView = sbView
        .findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
    textView.setTextColor(ContextCompat.getColor(activity, R.color.white))
    snackBar.show()
}