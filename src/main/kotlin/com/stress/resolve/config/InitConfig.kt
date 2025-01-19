package com.stress.resolve.config

import com.stress.resolve.domain.User
import com.stress.resolve.repository.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class InitConfig {

    @Autowired
    private lateinit var userRepository: UserRepository

    @PostConstruct
    fun init() {
        repeat(5) {
            userRepository.save(User(
                name = "테스트유저$it",
                age = it * 10,
                description = "테스트유저 $it 입니다."))
        }
    }
}