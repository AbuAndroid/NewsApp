package com.example.viewmodellivedata.utils

data class AppResult<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): AppResult<T> {
            return AppResult(Status.SUCCESS, data, null)
        }
        fun <T> error(msg: String, data: T?): AppResult<T> {
            return AppResult(Status.ERROR, data, msg)
        }
        fun <T> loading(data: T?): AppResult<T> {
            return AppResult(Status.LOADING, data, null)
        }

    }

}