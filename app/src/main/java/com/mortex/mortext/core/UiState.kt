package com.mortex.mortext.core
sealed class UiState<T> (val data:T? , val message:String?=null) {
    class Success<T>(data: T?): UiState<T>(data)
    class Error<T>(data: T?=null , message: String?):UiState<T>(data ,message)
    class Loading<T>(data:T? =null):UiState<T>(data)

}