package com.beefirst.sns.utils.base

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.beefirst.sns.R
import com.zeugmasolutions.localehelper.LocaleHelperActivityDelegate
import com.zeugmasolutions.localehelper.LocaleHelperActivityDelegateImpl
import com.zeugmasolutions.localehelper.Locales
import java.text.SimpleDateFormat
import java.util.Date

abstract class BaseFragment(
    @LayoutRes private val layoutRes: Int
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun initViews()
    abstract fun initObservables()

    fun getDate(pattern: String): String {
        return SimpleDateFormat(pattern).format(Date())
    }

    private fun getMonths(): ArrayAdapter<String> {
        var monthAdapter: ArrayAdapter<String>? = null

        val months = arrayOf(
            "January", "February",
            "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"
        )

        monthAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, months)
        return monthAdapter
    }

    fun changeLanguage() {
        val localeDelegate: LocaleHelperActivityDelegate = LocaleHelperActivityDelegateImpl()
        if (resources.configuration.locale != Locales.English)
            localeDelegate.setLocale(requireActivity(), Locales.English)
        else if (resources.configuration.locale != Locales.Arabic)
            localeDelegate.setLocale(requireActivity(), Locales.Arabic)
    }

    fun hideView(view: View) {
        view.visibility = View.INVISIBLE
    }

    fun showView(view: View) {
        view.visibility = View.VISIBLE
    }

    fun loadingForButtons(
        loadingView: View,
        disableEnableView: View
    ) {
        when (loadingView.isVisible) {
            true -> {
                loadingView.visibility = View.GONE
                disableEnableView.isEnabled = true
            }
            false -> {
                loadingView.visibility = View.VISIBLE
                disableEnableView.isEnabled = false
            }
        }
    }

    fun loadingForHideLayout(
        loadingView: View,
        disableEnableView: View
    ) {
        when (loadingView.isGone) {
            true -> {
                loadingView.visibility = View.VISIBLE
                disableEnableView.visibility = View.INVISIBLE
            }
            false -> {
                loadingView.visibility = View.GONE
                disableEnableView.visibility = View.VISIBLE
            }
        }
    }

    fun defaultImageIfNoData(
        recyclerView: View,
        defaultImg: View
    ) {
        hideView(recyclerView)
        showView(defaultImg)
    }

    fun showMessage(message: String?) {
        if (message != null && message.isNotEmpty()) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_LONG)
                .show()
        }
    }

    fun showConnectionError() {
        Toast.makeText(
            requireContext(),
            getString(R.string.please_check_internet),
            Toast.LENGTH_LONG
        )
            .show()
    }

    fun call(number: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number))
        startActivity(intent)
    }

    fun openWhtsApp(number: String) {
        val url = "https://api.whatsapp.com/send?phone=$number"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    fun copy(text: String) {
        val clipboard: ClipboardManager =
            activity?.getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
        showMessage("Copied")
    }

    fun loading(loadingView: View, disableEnableView: View) {
        when (loadingView.isVisible) {
            true -> {
                loadingView.visibility = View.GONE
                disableEnableView.isEnabled = true
            }
            false -> {
                loadingView.visibility = View.VISIBLE
                disableEnableView.isEnabled = false
            }
        }
    }
}