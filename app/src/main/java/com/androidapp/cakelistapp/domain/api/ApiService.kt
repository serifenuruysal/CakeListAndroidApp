package com.androidapp.cakelistapp.domain.api

import com.androidapp.cakelistapp.data.model.CakeModel
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Nur Uysal on 06/12/2021.
 */
interface ApiService {

    @GET("t-reed/739df99e9d96700f17604a3971e701fa/raw/1d4dd9c5a0ec758ff5ae92b7b13fe4d57d34e1dc/waracle_cake-android-client")
    suspend fun getCakeList(): Response<List<CakeModel>>

}