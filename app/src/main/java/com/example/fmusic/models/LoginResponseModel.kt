package com.example.fmusic.models

data class LoginResponseModel(
    val `data`: List<TaiKhoanModel>,
    val error: Boolean,
    val message: String
)