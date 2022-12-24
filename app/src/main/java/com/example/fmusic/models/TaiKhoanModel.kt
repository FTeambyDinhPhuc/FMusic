package com.example.fmusic.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaiKhoanModel(
    val email: String,
    val hinh: String,
    val id_taikhoan: Int,
    val matkhau: String,
    val ngaysinh: String,
    val tenhienthi: String
): Parcelable