package com.example.fmusic.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RadioModel(
    val duongdanradio: String,
    val hinhradio: String,
    val id_radio: Int,
    val ngaydangtai: String,
    val tenfile: String,
    val tenradio: String
): Parcelable