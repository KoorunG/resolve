package com.stress.resolve.response

data class PostResponse(
    val id: Long,
    private val originalTitle: String,
    val content: String,
) {
    val title: String
        get() = if (originalTitle.length > 10) "${originalTitle.substring(0, 10)}..." else originalTitle
}