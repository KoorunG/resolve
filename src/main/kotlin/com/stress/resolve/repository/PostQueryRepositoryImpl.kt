package com.stress.resolve.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import com.stress.resolve.domain.Post
import com.stress.resolve.request.PostSearch
import org.springframework.data.domain.PageRequest

class PostQueryRepositoryImpl(
    private val executor: KotlinJdslJpqlExecutor
): PostQueryRepository {
    override fun getList(postSearch: PostSearch): List<Post> =
        executor.findPage(PageRequest.of((postSearch.page - 1), postSearch.size)) {
            select(entity(Post::class)).
            from(entity(Post::class)).
            orderBy(path(Post::id).desc())
        }.filterNotNull()
}