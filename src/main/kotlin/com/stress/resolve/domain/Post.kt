package com.stress.resolve.domain

import com.stress.resolve.response.PostResponse
import jakarta.persistence.*

@Entity
class Post(
    title: String,
    content: String,
) {
    @Column(nullable = false)
    var title: String = title
        protected set

    @Lob
    @Column(nullable = false)
    var content: String = content
        protected set

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    fun update(title: String? = null, content: String? = null) {
        title?.let { this.title = it }
        content?.let { this.content = it }
    }

    companion object {
        // Post -> PostResponse
        fun response(post: Post): PostResponse =
            PostResponse(id = post.id!!, originalTitle = post.title, content = post.content)
    }
}