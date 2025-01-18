package com.stress.resolve.controller

import com.stress.resolve.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {

    /**
     * MethodArgumentNotValidException이 발생할 시 해당 메소드에 잡힘
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun invalidRequestHandler(e: MethodArgumentNotValidException): ErrorResponse {
        val errorResponse = ErrorResponse(code = "400", message = "잘못된 요청입니다.")
        e.fieldErrors.forEach { error ->
            errorResponse.addValidation(error.field, error.defaultMessage ?: "")
        }
        return errorResponse
    }
}
