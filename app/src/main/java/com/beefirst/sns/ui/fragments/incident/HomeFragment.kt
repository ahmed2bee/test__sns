package com.beefirst.sns.ui.fragments.incident

import android.util.Log
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.beefirst.sns.R
import com.beefirst.sns.adapters.IncidentAdapter
import com.beefirst.sns.utils.UserPreferences
import com.beefirst.sns.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

  private val viewModel: IncidentViewModel by hiltNavGraphViewModels(R.id.homeFragment)

  override fun initViews() {
    viewModel.getIncidents()
    initObservables()
  }

  override fun initObservables() {
    viewModel.incidents.observe(viewLifecycleOwner, {
      if (it.incidents.isNotEmpty()) {
        hideView(progressBar)
        incidents_rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        incidents_rv.adapter = IncidentAdapter(requireContext(), it)
      }
    })
  }
}