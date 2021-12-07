package com.androidapp.cakelistapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Nur Uysal on 06/12/2021.
 */
data class CakeModel(
    @SerializedName("title")
    val title: String?,
    @SerializedName("desc")
    val description: String?,
    @SerializedName("image")
    val imageUrl: String?
)