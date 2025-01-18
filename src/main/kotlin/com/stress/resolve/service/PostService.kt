package com.stress.resolve.service

import com.stress.resolve.domain.Post
import com.stress.resolve.repository.PostRepository
import com.stress.resolve.request.PostCreate
import com.stress.resolve.response.PostResponse
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {

    @Transactional
    fun write(postCreate: PostCreate) {
        Post(title = postCreate.title, content = postCreate.content).also { postRepository.save(it) }
    }

    fun get(id: Long): PostResponse {
        val post = postRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("존재하지 않는 글입니다!")
        return PostResponse(id = post.id!!, originalTitle = post.title, content = post.content)
    }
}