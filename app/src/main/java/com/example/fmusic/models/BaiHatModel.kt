package com.example.fmusic.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaiHatModel(
    val id_baihat: Int,
    val tenbaihat: String,
    val sangtac: String,
    val ngayphathanh: String,
    val hinhbaihat: String,
    val duongdannhac: String
): Parcelable