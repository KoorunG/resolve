package com.stress.resolve.controller

import com.stress.resolve.exception.ResolveException
import com.stress.resolve.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun invalidRequestHandler(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(code = "400", message = "잘못된 요청입니다.")
        e.fieldErrors.forEach { error ->
            errorResponse.addValidation(error.field, error.defaultMessage ?: "")
        }
        return ResponseEntity.status(400).body(errorResponse)
    }


    /**
     * ResolveException 발생할 시 해당 메소드에 잡힘
     * 공통적으로 처리할 필요가 있는 BusinessException의 경우 abstract class로 ResolveException을 만들어 처리함
     */
    @ExceptionHandler(ResolveException::class)
    fun resolveExceptionHandler(e: ResolveException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(code = e.code.toString(), message = e.message)
        return ResponseEntity.status(e.code).body(errorResponse)
    }
}
