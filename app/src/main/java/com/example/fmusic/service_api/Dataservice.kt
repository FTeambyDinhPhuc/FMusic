package com.example.fmusic.service_api

import com.example.fmusic.models.AlbumModel
import com.example.fmusic.models.BaiHatModel
import com.example.fmusic.models.CaSiModel
import retrofit2.Call
import retrofit2.http.*



interface Dataservice {
    @GET("nhac/LayDanhSachAlbum")
    fun getAllAlbum(): Call<List<AlbumModel>>

    @GET("nhac")
    fun getAllBaiHat(): Call<List<BaiHatModel>>

    @GET("nhac/LayCaSiTheoBaiHat/{idBaiHat}")
    fun getCaSiByBaiHat(@Path("idBaiHat") idBaiHat: Int): Call<List<CaSiModel>>

    @GET("nhac/LayBaiHatYeuThichTheoIDTK/{idTaiKhoan}")
    fun getListBaiHatYeuThichByTK(@Path("idTaiKhoan") idTaiKhoan: Int): Call<List<BaiHatModel>>

    @GET("nhac/LayDanhSachBaiHatTheoAlbum/{idAlbum}")
    fun getListBaiHatByAlbum(@Path("idAlbum") idAlbum: Int): Call<List<BaiHatModel>>


}