package com.stress.resolve.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test

@ActiveProfiles("test")
@SpringBootTest
class RedisConnectionTest {

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>

    @BeforeEach
    fun setUp() {
        redisTemplate.connectionFactory?.connection?.flushDb()
    }

    @Test
    fun `Redis에 데이터를 저장하고 가져오는 테스트`() {
        // given
        val key = "user:koorung:name"
        val value = "koorung"

        // when
        redisTemplate.opsForValue().set(key, value)

        // then
        val selected = redisTemplate.opsForValue().get(key)
        assertThat(selected).isEqualTo("koorung")
    }
}