package com.stress.resolve.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import com.stress.resolve.domain.Post
import com.stress.resolve.request.PostSearch
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Query

class PostQueryRepositoryImpl: PostQueryRepository {
    @PersistenceContext
    private lateinit var entityManager: EntityManager
    private val context = JpqlRenderContext()

    override fun getList(postSearch: PostSearch): List<Post> {
        val query = jpql { select(entity(Post::class)).from(entity(Post::class)).orderBy(path(Post::id).desc()) }
        val jpaQuery: Query = entityManager.createQuery(query, context).setFirstResult(postSearch.offset).setMaxResults(postSearch.size)
        return jpaQuery.resultList.map { it as Post }
    }
}