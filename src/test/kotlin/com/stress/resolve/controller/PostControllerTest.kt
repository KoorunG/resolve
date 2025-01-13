package com.stress.resolve.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class PostControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

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
}