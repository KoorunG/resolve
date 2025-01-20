package com.stress.resolve.request

data class PostSearch(
    val page: Int = 1,
    val size: Int = 10,
) {
    val offset: Int
        get() = (page - 1) * size
}