package com.stress.resolve.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class TestControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `GET 요청 전송 시 Hello world를 출력한다`() {
        mockMvc.perform(get("/test"))
            .andExpect(status().isOk)
            .andExpect(content().string("Hello World"))
            .andDo(print())
    }

    @Test
    fun `POST 요청 전송 시 Hello world를 출력한다`() {
        mockMvc.perform(
            post("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"title\" : \"제목입니다.\", \"content\" : \"내용입니다\"}")
        ).andExpect {
            status().isOk
            content().string("Hello World")
        }.andDo(print())
    }
}