package com.stress.resolve.repository

import com.stress.resolve.domain.Post

interface PostQueryRepository {
    fun getList(page: Int, pageSize: Int): List<Post>
}