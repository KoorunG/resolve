package com.stress.resolve.service

import com.stress.resolve.domain.Post
import com.stress.resolve.repository.PostRepository
import com.stress.resolve.request.PostCreate
import com.stress.resolve.request.PostSearch
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
        val post = Post(title = postCreate.title, content = postCreate.content)
        postRepository.save(post)
    }

    // 글이 많은 경우 :: 비용이 많이 든다.
    // 페이징 처리가 필요함
    fun getList(postSearch: PostSearch): List<PostResponse> {
//        return postRepository.findAll(pageable).map { Post.response(it) }
        // === Kotlin JDSL을 적용한 paging 처리 === //
        return postRepository.getList(postSearch).map { Post.response(it) }
    }


    fun get(id: Long): PostResponse {
        val post = postRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("존재하지 않는 글입니다!")
        return Post.response(post)
    }
}