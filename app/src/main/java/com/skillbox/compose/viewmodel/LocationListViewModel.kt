package com.skillbox.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.skillbox.compose.network.Networking
import com.skillbox.compose.repository.LocationsDataSource

class LocationListViewModel : ViewModel() {
    private val api = Networking.characterApi
    val locationPagingFlow = Pager(PagingConfig(pageSize = 20)) {
        LocationsDataSource(api)
    }.flow.cachedIn(viewModelScope)
}
