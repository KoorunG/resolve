package com.stress.resolve.controller

import com.stress.resolve.request.PostCreate
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger { }

@RestController
class TestController {

    @GetMapping("/test")
    fun get() = "Hello World";

    @PostMapping("/test")
    fun post(@RequestBody request: PostCreate): String {
        logger.info { "request: $request" }

        return "Hello world"
    }
}