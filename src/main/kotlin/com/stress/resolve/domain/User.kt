package com.stress.resolve.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class User(
    name: String,
    age: Int,
    description: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null

    @Column(nullable = false)
    var name: String = name
        protected set

    @Column(nullable = false)
    var age: Int = age
        protected set

    @Lob
    var description: String = description
        protected set
}