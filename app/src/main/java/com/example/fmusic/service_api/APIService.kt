package com.example.fmusic.service_api

object APIService {
    private val base_url: String = "https://cn-api.fteamlp.top/api/"
    val apiRetrofitClient = APIRetrofitClient()
    val getService: Dataservice
        get() =  apiRetrofitClient.getClient(base_url).create(Dataservice::class.java)

}

