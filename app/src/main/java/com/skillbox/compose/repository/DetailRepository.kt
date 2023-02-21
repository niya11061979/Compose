package com.skillbox.compose.repository

import android.util.Log
import com.skillbox.compose.data.DetailCharacter
import com.skillbox.compose.data.Episode
import com.skillbox.compose.network.Networking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailRepository {

    fun loadDetailCharacter(
        id: Long,
        onComplete: (DetailCharacter) -> Unit,
    ) {
        Networking.characterApi.loadDetail(id).enqueue(
            object : Callback<DetailCharacter> {
                override fun onResponse(
                    call: Call<DetailCharacter>,
                    response: Response<DetailCharacter>
                ) {
                    Log.d("msg", "${response.isSuccessful}")
                    if (response.isSuccessful) response.body()?.let { onComplete(it) }
                }

                override fun onFailure(call: Call<DetailCharacter>, t: Throwable) {
                    Log.d("onFailure", "load detail error ${t.message}")
                }

            }
        )
    }

    fun loadEpisode(
        episodeUrlList: Array<String>,
        complete: (Episode) -> Unit
    ) {
        for (url in episodeUrlList) {
            val episodeId = url.substringAfterLast("/").toLong()
                Networking.characterApi.loadEpisode(episodeId).enqueue(
                    object : Callback<Episode> {
                        override fun onResponse(
                            call: Call<Episode>,
                            response: Response<Episode>
                        ) {
                            if (response.isSuccessful) response.body()?.let { complete(it) }
                        }

                        override fun onFailure(call: Call<Episode>, t: Throwable) {
                            Log.d("onFailure", "load episode error ${t.message}")
                        }
                    }
                )

        }
    }
}