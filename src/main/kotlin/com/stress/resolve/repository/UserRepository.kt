package com.stress.resolve.repository

import com.stress.resolve.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>