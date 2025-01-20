package com.stress.resolve.repository

import com.stress.resolve.domain.Post
import com.stress.resolve.request.PostSearch

interface PostQueryRepository {
    fun getList(postSearch: PostSearch): List<Post>
}