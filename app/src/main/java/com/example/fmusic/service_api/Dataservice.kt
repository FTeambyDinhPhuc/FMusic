package com.example.fmusic.service_api

import com.example.fmusic.models.TaiXeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Dataservice {
    @GET("taixe/getAllTaiXe")
     fun getAllTaiXe(): Call<TaiXeModel>

    @GET("taixe/getTaiXe/{idTaiXe}")
    fun getTaiXe(@Path("idTaiXe") intTaiXe: Int): Call<TaiXeModel>
}