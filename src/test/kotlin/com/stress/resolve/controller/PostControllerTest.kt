package com.stress.resolve.controller

import com.stress.resolve.repository.PostRepository
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

//@WebMvcTest
@SpringBootTest // 3-tier 테스트를 위한 애노테이션 추가
@AutoConfigureMockMvc // mockMvc 주입을 위한 애노테이션 추가
class PostControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var postRepository: PostRepository

    @BeforeEach
    fun cleanUp() {
        postRepository.deleteAll()
    }

    @Test
    fun `POST 요청 전송 시 title값은 필수이다`() {
        mockMvc.perform(
            post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\" : \"   \", \"content\" : \"   \"}")
        ).andExpect(status().isBadRequest)
//            .andExpect(content().string("Hello World"))
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
            .andExpect(jsonPath("$.validation.size()").value(2))
            .andExpect(jsonPath("$..title").value("제목은 빈값이 올 수 없습니다!"))
            .andExpect(jsonPath("$..content").value("내용은 빈값이 올 수 없습니다!"))
            .andDo(print())
    }

    @Test
    fun `정상 POST 요청 시 DB에 값을 저장한다`() {
        // given
        assertThat(postRepository.count()).isEqualTo(0L)

        // when
        mockMvc.perform(post("/posts").contentType(APPLICATION_JSON).content("{\"title\" : \"제목입니다.\", \"content\" : \"내용입니다.\"}"))
            .andExpect(status().isOk)
            .andDo(print())

        // then
        assertThat(postRepository.count()).isEqualTo(1L)
        assertThat(postRepository.findAll()).extracting("title").containsExactly("제목입니다.")
        assertThat(postRepository.findAll()).extracting("content").containsExactly("내용입니다.")
    }
}
