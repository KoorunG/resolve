package com.stress.resolve.controller

import com.stress.resolve.request.PostCreate
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {  }

@RestController
class PostController {

    @PostMapping("/posts")
    fun post(@RequestBody @Valid request: PostCreate, bindingResult: BindingResult): Map<String, String> {
        val result = mutableMapOf<String, String>()
        if(bindingResult.hasErrors()) {
            bindingResult.fieldErrors.forEach { error -> result[error.field] = error.defaultMessage ?: "" }
        }
        return result
    }
}