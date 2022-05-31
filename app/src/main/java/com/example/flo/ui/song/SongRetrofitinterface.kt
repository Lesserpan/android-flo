package com.example.flo.ui.song

import com.example.flo.SongResponse
import retrofit2.Call
import retrofit2.http.GET

interface SongRetrofitinterface {
    @GET("/songs")
    fun getSongs(): Call<SongResponse>

}//API 가져오기1