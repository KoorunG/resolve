package com.stress.resolve.domain

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
    private val id: Long? = null
}