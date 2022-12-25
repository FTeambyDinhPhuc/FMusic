package com.example.fmusic.service_api

import com.example.fmusic.models.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*



interface Dataservice {
    @GET("nhac/LayDanhSachAlbum")
    fun getAllAlbum(): Call<List<AlbumModel>>

    @GET("nhac")
    fun getAllBaiHat(): Call<List<BaiHatModel>>

    @GET("nhac/GetAllRadio")
    fun getAllRadio(): Call<List<RadioModel>>

    @GET("nhac/LayBaiHatYeuThichTheoIDTK/{idTaiKhoan}")
    fun getALlBaiHatYeuThichByTK(@Path("idTaiKhoan") idTaiKhoan: Int): Call<List<BaiHatModel>>

    @GET("nhac/LayCaSiTheoBaiHat/{idBaiHat}")
    fun getCaSiByBaiHat(@Path("idBaiHat") idBaiHat: Int): Call<List<CaSiModel>>

    @GET("nhac/LayBaiHatYeuThichTheoIDTK/{idTaiKhoan}")
    fun getListBaiHatYeuThichByTK(@Path("idTaiKhoan") idTaiKhoan: Int): Call<List<BaiHatModel>>

    @GET("nhac/LayDanhSachBaiHatTheoAlbum/{idAlbum}")
    fun getListBaiHatByAlbum(@Path("idAlbum") idAlbum: Int): Call<List<BaiHatModel>>

    @GET("nhac/GetRadioById/{idTaiKhoan}")
    fun getListRadioByTK(@Path("idTaiKhoan") idTaiKhoan: Int): Call<List<RadioModel>>

    @FormUrlEncoded
    @POST("nguoidung/login")
    fun login(
        @Field("email") taiKhoan: String,
        @Field("matkhau") matKhau: String
    ): Call<LoginResponseModel>

    @FormUrlEncoded
    @POST("nguoidung/dangky")
    fun signup(
        @Field("email") taiKhoan: String,
        @Field("matkhau") matKhau: String,
        @Field("tenhienthi") tenHienThi: String,
        @Field("ngaysinh") ngaySinh: String
    ): Call<ResponseModel>

    @FormUrlEncoded
    @POST("nhac/ThemBaiHatYeuThich")
    fun themBaiHatYeuThich(
        @Field("id_taikhoan") idTaiKhoan: Int,
        @Field("id_baihat") idBaiHat: Int
    ): Call<ResponseModel>

    @FormUrlEncoded
    @POST("nhac/XoaBaiHatKhoiDanhSachYeuThich")
    fun xoaBaiHatYeuThich(
        @Field("id_taikhoan") idTaiKhoan: Int,
        @Field("id_baihat") idBaiHat: Int
    ): Call<ResponseModel>

    @FormUrlEncoded
    @POST("nhac/upradio")
    fun themRaido(
        @Field("id_taikhoan") idTaiKhoan: Int,
        @Field("tenradio") tenRadio: String,
        @Field("ngaydangtai") ngayDang: String,
        @Field("duongdanradio") duongDanRadio: String,
        @Field("tenfile") tenFile: String,
        @Field("hinhradio") hinhRadio: String
    ): Call<ResponseModel>

    @Multipart
    @POST("upload")
    fun upFile(
        @Part file: MultipartBody.Part,
    ): Call<ResponseUpFileModel>


    @DELETE("nhac/delradio/{idRadio}")
    fun xoaRadio(@Path("idRadio") idRadio: Int): Call<ResponseModel>

}