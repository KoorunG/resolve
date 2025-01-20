package com.stress.resolve.repository

import com.stress.resolve.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long>, PostQueryRepository