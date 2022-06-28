package com.beefirst.sns.ui.fragments.incident

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beefirst.sns.model.IncidentModel
import com.beefirst.sns.repository.IncidentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncidentViewModel
@Inject constructor(
    private val incidentsRepository: IncidentsRepository,
) : ViewModel() {

    private val _incidents: MutableLiveData<IncidentModel> by lazy { MutableLiveData() }
    val incidents: LiveData<IncidentModel> get() = _incidents

    fun getIncidents() {
        viewModelScope.launch {
            _incidents.postValue(incidentsRepository.getIncidents())
        }
    }
}
