package com.skillbox.compose.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.compose.data.DetailCharacter
import com.skillbox.compose.data.DetailWithEpisode
import com.skillbox.compose.data.Episode
import com.skillbox.compose.repository.DetailRepository
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val repository = DetailRepository()
    private var detailCharacter: DetailCharacter? = null
    private val detailMutableLiveData = MutableLiveData<DetailWithEpisode>()
    val detailLiveData: LiveData<DetailWithEpisode>
        get() = detailMutableLiveData

    fun loadDetail(id: Long) {
        viewModelScope.launch {
            try {
                Log.d("msg", id.toString())
                repository.loadDetailCharacter(id) {
                    detailCharacter = it
                    loadEpisode(it.episode)
                }

            } catch (t: Throwable) {
                Log.d("DetailViewModel", "loadDetail error ${t.message}")
            }
        }
    }

    private fun loadEpisode(arrayEpisodeUrl: Array<String>) {
        val listEpisode = mutableListOf<Episode>()
        viewModelScope.launch {
            try {
                repository.loadEpisode(arrayEpisodeUrl) {
                    Log.d("111111","arrayEpisodeUrl.size-----${arrayEpisodeUrl.size} ")
                    listEpisode.add(Episode(it.id, it.name,it.airDate, it.episode))
                    Log.d("111111","listEpisode.size-----${listEpisode.size} ")

                    if (listEpisode.size == arrayEpisodeUrl.size) {
                        detailMutableLiveData.postValue(detailCharacter?.let { detailCharacter ->
                            DetailWithEpisode(detailCharacter, listEpisode)
                        })
                    }
                }
            } catch (t: Throwable) {
                Log.d("DetailViewModel", "loadEpisode error ${t.message}")
            }
        }
    }
}
