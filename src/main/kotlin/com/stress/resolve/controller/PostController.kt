package com.stress.resolve.controller

import com.stress.resolve.request.PostCreate
import com.stress.resolve.service.PostService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {  }

@RestController
class PostController(
    private val postService: PostService
) {

    @PostMapping("/posts")
    fun post(@RequestBody @Valid request: PostCreate) {
        postService.write(request)
    }
}