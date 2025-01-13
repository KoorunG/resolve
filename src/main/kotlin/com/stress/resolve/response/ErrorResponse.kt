package com.stress.resolve.response

data class ErrorResponse(
    val code: String,
    val message: String,
    val validation: MutableMap<String, String> = mutableMapOf()
) {
    fun addValidation(fieldName: String, errorMessage: String) {
        validation[fieldName] = errorMessage
    }
}