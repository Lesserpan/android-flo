package com.example.flo.data.remote

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("isSuccess")val isSuccess:Boolean,
    @SerializedName("code")val code:Int,
    @SerializedName("message")val message:String,
    @SerializedName("result") val result: Result?
)//API 가져오기 2


data class Result(
    @SerializedName("userIdx")var userIdx: Int,
    @SerializedName("jwt")var jwt : String
)