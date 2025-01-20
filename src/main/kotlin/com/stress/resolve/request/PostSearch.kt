package com.stress.resolve.request

data class PostSearch(
    val page: Int = 1,
    val size: Int = 10,
) {
    val offset: Int
        get() = (if((page - 1) >= 0) (page - 1) else 0) * size
}