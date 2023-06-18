package com.oza.challenge.common

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val errorCode: Int, val errorMessage: String) : Resource<T>()
}
