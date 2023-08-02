package com.youarelaunched.challenge.ui.screen.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VendorsVM @Inject constructor(
    private val repository: VendorsRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(
        VendorsScreenUiState(
            vendors = null
        )
    )
    var uiState = _uiState.asStateFlow()

    init {
        getVendors()
    }

    private fun getVendors() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    vendors = repository.getVendors()
                )
            }
        }
    }

    fun getSearchVendors(value: String) {
        viewModelScope.launch {
            _uiState.update {
                val filteredVendors = if (value.isEmpty()) {
                    repository.getVendors()
                } else {
                    val resultList = ArrayList<Vendor>()
                    for (vendor in uiState.value.vendors!!) {
                        if (vendor.companyName.lowercase(Locale.getDefault())
                                .contains(
                                    value.lowercase(
                                        Locale.getDefault()
                                    )
                                )
                        ) {
                            resultList.add(vendor)
                        }
                    }
                    resultList
                }
                it.copy(
                    vendors = filteredVendors
                )
            }
        }
    }
}