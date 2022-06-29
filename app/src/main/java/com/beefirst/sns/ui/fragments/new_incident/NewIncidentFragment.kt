package com.beefirst.sns.ui.fragments.new_incident

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.beefirst.sns.R
import com.beefirst.sns.enums.Consts
import com.beefirst.sns.model.Incident
import com.beefirst.sns.model.Media
import com.beefirst.sns.utils.DatesUtils
import com.beefirst.sns.utils.base.BaseFragment
import com.google.type.DateTime
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_new_incident.*

class NewIncidentFragment : BaseFragment(R.layout.fragment_new_incident) {

    var selectedStatus = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initViews() {
        initObservables()

        initStatusSpinner()

        button.setOnClickListener {
            if (etDescription.text.toString().isNullOrEmpty()) {
                etDescription.error = "Description required"
                return@setOnClickListener
            }

            if (selectedStatus == -1) {
                Toast.makeText(requireContext(), "Please select a status type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            addNew(etDescription.text.toString(), selectedStatus)
            val bundle = Bundle()
            bundle.putBoolean("isDataLocally", true)
            findNavController().navigate(R.id.newIncidentToHome, bundle)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addNew(description: String, status: Int) {
        val media = Media("1", "2", "image/png", 0, "https://media.istockphoto.com/vectors/new-comic-speech-bubble-vector-flat-illustrations-color-phrase-with-vector-id1254219019?k=20&m=1254219019&s=612x612&w=0&h=WmFIoXyry2Y_oGZV7A_L4i2QLd4msYz9GioRZLjbtN0=")
        Consts.allIncidents.add(Incident("123456", DatesUtils.getDateTimeNow(),
            description, "4321", "1111", 30.0000, 40.0000, listOf(media),
            1, status, 1, "17-7-1991"))
        Toast.makeText(requireContext(), "Incident added successfully", Toast.LENGTH_SHORT).show()
    }

    private fun initStatusSpinner() {

        val status = ArrayList<String>()
        status.add("Select status")
        status.add("Submitted")
        status.add("InProgress")
        status.add("Completed")
        status.add("Rejected")
        spinnerStatusNew.adapter = putSpinnerData(status)
        spinnerStatusNew.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedStatus = if (position == 0) -1 else position - 1
                Log.i("onItemSelected", "onItemSelected: $selectedStatus")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    override fun initObservables() {

    }
}