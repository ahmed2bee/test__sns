package com.beefirst.sns.ui.fragments.incident

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.beefirst.sns.R
import com.beefirst.sns.adapters.IncidentAdapter
import com.beefirst.sns.enums.StatusTypes
import com.beefirst.sns.model.Incident
import com.beefirst.sns.utils.DatesUtils
import com.beefirst.sns.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

  private val viewModel: IncidentViewModel by hiltNavGraphViewModels(R.id.homeFragment)

  var incidents = ArrayList<Incident>()
  var allIncidents = ArrayList<Incident>()

  var selectedStatus = ""
  var selectedDate = ""

  override fun initViews() {
    viewModel.getIncidents()
    initObservables()

    btnFilter.setOnClickListener { filterIncidents(selectedDate, selectedStatus) }
    tvRemoveFilter.setOnClickListener { initRecyclerView(allIncidents) }
  }

  override fun initObservables() {
    viewModel.incidents.observe(viewLifecycleOwner, {
      if (it.incidents.isNotEmpty()) {
        hideView(progressBar)

        incidents.addAll(it.incidents)
        allIncidents.addAll(it.incidents)
        initRecyclerView(incidents)
        initStatusSpinner()
        initDateSpinner()
      }
    })
  }

  @SuppressLint("NotifyDataSetChanged")
  private fun initRecyclerView(list: ArrayList<Incident>) {
    incidents_rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    val incidentAdapter = IncidentAdapter(requireContext(), list)
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
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
      for (i in allIncidents.indices) {
        if (date == DatesUtils.convertDateTime(allIncidents[i].createdAt).substring(0, 11)) {
          incidents.add(allIncidents[i])
        }
      }
    } else if (date == "" && status != "") {
      for (i in allIncidents.indices) {
        if (status == StatusTypes.statusTypes(allIncidents[i].status)) {
          incidents.add(allIncidents[i])
        }
      }
    } else {
      for (i in allIncidents.indices) {
        if (status == StatusTypes.statusTypes(allIncidents[i].status) &&
          date == DatesUtils.convertDateTime(allIncidents[i].createdAt).substring(0, 11)) {
          incidents.add(allIncidents[i])
        }
      }
    }
    initRecyclerView(incidents)
  }
}














