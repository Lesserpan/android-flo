package com.example.flo

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SongRetrofitinterface {
    @GET("/songs")
    fun getSongs(): Call<SongResponse>

}//API 가져오기1