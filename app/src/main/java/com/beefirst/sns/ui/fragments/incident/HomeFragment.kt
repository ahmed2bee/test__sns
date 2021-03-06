package com.beefirst.sns.ui.fragments.incident

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.beefirst.sns.adapters.IncidentAdapter
import com.beefirst.sns.enums.Consts
import com.beefirst.sns.enums.StatusTypes
import com.beefirst.sns.model.Incident
import com.beefirst.sns.model.Media
import com.beefirst.sns.utils.DatesUtils
import com.beefirst.sns.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*


@AndroidEntryPoint
class HomeFragment : BaseFragment(com.beefirst.sns.R.layout.fragment_home), IncidentAdapter.IncidentClicked {

    private val viewModel: IncidentViewModel by hiltNavGraphViewModels(com.beefirst.sns.R.id.homeFragment)

    var incidents = ArrayList<Incident>()

    var selectedStatus = ""
    var selectedDate = ""

    var isDataLocally = false


    override fun initViews() {

        if (arguments != null)
            isDataLocally = arguments?.getBoolean("isDataLocally")!!

        // check if user add a new incident and come back into home page or not
        if (!isDataLocally) {
            viewModel.getIncidents()
            initObservables()
        } else {
            hideView(progressBar)
            incidents.addAll(Consts.allIncidents)
            initRecyclerView(Consts.allIncidents)
            initStatusSpinner()
            initDateSpinner()
        }

        btnFilter.setOnClickListener { filterIncidents(selectedDate, selectedStatus) }
        tvRemoveFilter.setOnClickListener { initRecyclerView(Consts.allIncidents) }
        fabAddNew.setOnClickListener {
            findNavController().navigate(com.beefirst.sns.R.id.homeToNewIncident)
        }
    }

    override fun initObservables() {
        viewModel.incidents.observe(viewLifecycleOwner, {
            if (it.incidents.isNotEmpty()) {
                hideView(progressBar)
                incidents.addAll(it.incidents)
                Consts.allIncidents.addAll(it.incidents)
                initRecyclerView(incidents)
                initStatusSpinner()
                initDateSpinner()
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView(list: ArrayList<Incident>) {
        incidents_rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val incidentAdapter = IncidentAdapter(requireContext(), list, this)
        incidents_rv.adapter = incidentAdapter
        incidentAdapter.notifyDataSetChanged()
    }

    private fun initStatusSpinner() {

        val status = ArrayList<String>()
        status.add("Select status")
        status.add("Submitted")
        status.add("InProgress")
        status.add("Completed")
        status.add("Rejected")
        spinnerStatus.adapter = putSpinnerData(status)
        spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedStatus = if (position == 0) "" else status[position]
                Log.i("onItemSelected", "onItemSelected: $selectedStatus")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun initDateSpinner() {

        // using SET here to ignore dates duplicate
        val dates = HashSet<String>()
        dates.add("Select Date")

        // using ArrayList to get selected date "by position"
        val datesList = ArrayList<String>()

        for (index in incidents.indices)
            dates.add(DatesUtils.convertDateTime(incidents[index].createdAt).substring(0, 11))

        datesList.addAll(dates)

        spinnerDate.adapter = putSpinnerData(dates)
        spinnerDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedDate = if (position == 0) "" else datesList[position]
                Log.i("onItemSelectedDATEEE", "onItemSelected: $selectedDate")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun filterIncidents(date: String, status: String) {
        if (date == "" && status == "") return

        incidents.clear()

        if (date != "" && status == "") {
            for (i in Consts.allIncidents.indices) {
                if (date == DatesUtils.convertDateTime(Consts.allIncidents[i].createdAt)
                        .substring(0, 11)
                ) {
                    incidents.add(Consts.allIncidents[i])
                }
            }
        } else if (date == "" && status != "") {
            for (i in Consts.allIncidents.indices) {
                if (status == StatusTypes.statusTypes(Consts.allIncidents[i].status)) {
                    incidents.add(Consts.allIncidents[i])
                }
            }
        } else {
            for (i in Consts.allIncidents.indices) {
                if (status == StatusTypes.statusTypes(Consts.allIncidents[i].status) &&
                    date == DatesUtils.convertDateTime(Consts.allIncidents[i].createdAt)
                        .substring(0, 11)
                ) {
                    incidents.add(Consts.allIncidents[i])
                }
            }
        }
        initRecyclerView(incidents)
    }

    private fun changeStatusAlertDialog(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose status type")
        val options = arrayOf("Submitted", "InProgress", "Completed", "Rejected")
        builder.setItems(
            options
        ) { _, which ->
            when (which) {
                0 -> changeStatus(position, 0)
                1 -> changeStatus(position, 1)
                2 -> changeStatus(position, 2)
                3 -> changeStatus(position, 3)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onIncidentClicked(position: Int) {
        changeStatusAlertDialog(position)
    }

    private fun changeStatus(position: Int, newStatusType: Int) {
        incidents[position].status = newStatusType
        Consts.allIncidents[position].status = newStatusType
        initRecyclerView(incidents)
    }
}














