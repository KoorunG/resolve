package com.stress.resolve.request

import jakarta.validation.constraints.Pattern

data class PostEdit(
    @field:Pattern(regexp = "^\\s*\\S.*$" , message = "제목은 빈값이 올 수 없습니다!")
    val title: String? = null,
    @field:Pattern(regexp = "^\\s*\\S.*$" , message = "내용은 빈값이 올 수 없습니다!")
    val content: String? = null,
)
