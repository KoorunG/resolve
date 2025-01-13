package com.stress.resolve.controller

import com.stress.resolve.request.PostCreate
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {  }

@RestController
class PostController {

    @PostMapping("/posts")
    fun post(@RequestBody @Valid request: PostCreate): Map<String, String> {
        val result = mutableMapOf<String, String>()
        /* 1. BindingResult를 파라미터로 받으면 ControllerAdvice 전에 먼저 걸리게 된다. */
//        if(bindingResult.hasErrors()) {
//            bindingResult.fieldErrors.forEach { error -> result[error.field] = error.defaultMessage ?: "" }
//        }
        /* 2. 응답 클래스로 Map을 리턴하기보다는, 응답 클래스를 만들어주는 것이 좋다. */
        return result
    }
}