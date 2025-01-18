package com.stress.resolve.service

import com.stress.resolve.domain.Post
import com.stress.resolve.repository.PostRepository
import com.stress.resolve.request.PostCreate
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {

    @Transactional
    fun write(postCreate: PostCreate) {
        Post(title = postCreate.title, content = postCreate.content).also { postRepository.save(it) }
    }
}