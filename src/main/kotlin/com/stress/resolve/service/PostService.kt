package com.stress.resolve.service

import com.stress.resolve.domain.Post
import com.stress.resolve.repository.PostRepository
import com.stress.resolve.request.PostCreate
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }

@Service
class PostService(
    private val postRepository: PostRepository
) {

    @Transactional
    fun write(postCreate: PostCreate) {
        val post = Post(title = postCreate.title, content = postCreate.content)
        postRepository.save(post)
    }
}