package com.skillbox.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.skillbox.compose.network.Networking
import com.skillbox.compose.repository.CharacterDataSource


class CharacterListViewModel : ViewModel() {
    private val api = Networking.characterApi

    val characterPagingFlow = Pager(PagingConfig(pageSize = 20)) {
        CharacterDataSource(api)
    }.flow.cachedIn(viewModelScope)
}
