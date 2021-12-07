package com.androidapp.cakelistapp.data.repository

/**
 * Created by Nur Uysal on 06/12/2021.
 */
sealed class ApiResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResponse<T>()
    data class Error(val error: String) : ApiResponse<Nothing>()
}