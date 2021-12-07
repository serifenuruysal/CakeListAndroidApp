package com.androidapp.cakelistapp.app.viewmodel

/**
 * Created by Nur Uysal on 06/12/2021.
 */
data class MainViewState<out T>(
    val status: Status,
    val data: T?,
    val errorMessage: String?
) {
    companion object {

        fun <T> success(data: T?): MainViewState<T> {
            return MainViewState(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): MainViewState<T> {
            return MainViewState(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): MainViewState<T> {
            return MainViewState(Status.LOADING, data, null)
        }

    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}