package com.stress.resolve.request

import jakarta.validation.constraints.NotBlank

data class PostCreate(
    @field:NotBlank(message = "제목은 빈값이 올 수 없습니다!")
    val title: String,
    @field:NotBlank(message = "내용은 빈값이 올 수 없습니다!")
    val content: String,
)