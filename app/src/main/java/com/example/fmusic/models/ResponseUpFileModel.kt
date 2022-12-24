package com.example.fmusic.models

data class ResponseUpFileModel(
    val error: Boolean,
    val filename: String,
    val message: String,
    val url: String
)