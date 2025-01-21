package com.stress.resolve.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.stress.resolve.domain.Post
import com.stress.resolve.repository.PostRepository
import com.stress.resolve.request.PostCreate
import com.stress.resolve.request.PostEdit
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

//@WebMvcTest
@ActiveProfiles("test")
@SpringBootTest // 3-tier 테스트를 위한 애노테이션 추가
@AutoConfigureMockMvc // mockMvc 주입을 위한 애노테이션 추가
class PostControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var mapper: ObjectMapper

    @BeforeEach
    fun cleanUp() {
        postRepository.deleteAll()
    }

    @Test
    fun `POST 요청 전송 시 title값은 필수이다`() {
        // given
        val request = PostCreate(title = "   ", content = "   ")

        // when & then
        mockMvc.perform(
            post("/posts")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest)
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
        val request = PostCreate(title = "제목입니다.", content = "내용입니다.")
        mockMvc.perform(
            post("/posts").contentType(APPLICATION_JSON).content(
                mapper.writeValueAsString(request)
            )
        )
            .andExpect(status().isOk)
            .andDo(print())

        // then
        assertThat(postRepository.count()).isEqualTo(1L)
        assertThat(postRepository.findAll()).extracting("title").containsExactly("제목입니다.")
        assertThat(postRepository.findAll()).extracting("content").containsExactly("내용입니다.")
    }

    @Test
    fun `글을 1개 조회한다`() {
        // given
        val post = postRepository.save(Post(title = "글 제목1", content = "글 내용1"));

        // when & then
        mockMvc.perform(get("/posts/{postId}", post.id).contentType(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("글 제목1"))
            .andExpect(jsonPath("$.content").value("글 내용1"))
            .andDo(print())
    }

    @Test
    fun `title은 최대 10글자까지만 반환된다`() {
        // given
        val post = postRepository.save(Post(title = "10글자가 넘는 제목에 대한 테스트입니다.", content = "글 내용1"));

        // when & then
        mockMvc.perform(get("/posts/{postId}", post.id).contentType(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("10글자가 넘는 제..."))
            .andDo(print())
    }

    @Test
    fun `posts GET요청으로 모든 글을 조회한다`() {
        // given
        postRepository.saveAll(List(30) { Post(title = "제목입니다 ${it + 1}", content = "내용입니다 ${it + 1}") })

        // when & then
        mockMvc.perform(get("/posts?page=1").contentType(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(10))
            .andExpect(jsonPath("$..title", Matchers.containsInAnyOrder("제목입니다 30", "제목입니다 29", "제목입니다 28", "제목입니다 27", "제목입니다 26", "제목입니다 25", "제목입니다 24", "제목입니다 23", "제목입니다 22", "제목입니다 21")))
            .andExpect(jsonPath("$..content", Matchers.containsInAnyOrder("내용입니다 30", "내용입니다 29", "내용입니다 28", "내용입니다 27", "내용입니다 26", "내용입니다 25", "내용입니다 24", "내용입니다 23", "내용입니다 22", "내용입니다 21")))
            .andDo(print())
    }

    @Test
    fun `게시글을 수정한다`(){
        // given
        val post = Post(title = "제목입니다", content = "내용입니다")
        postRepository.save(post)

        // when & then
        mockMvc.perform(
            patch("/posts/{postId}", post.id!!).contentType(APPLICATION_JSON).content(mapper.writeValueAsString(PostEdit(title = "제목만 수정함")))
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$..title").value("제목만 수정함"))
            .andExpect(jsonPath("$..content").value("내용입니다"))
            .andDo(print())
    }
}
