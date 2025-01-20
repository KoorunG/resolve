package com.stress.resolve.service

import com.stress.resolve.domain.Post
import com.stress.resolve.repository.PostRepository
import com.stress.resolve.request.PostCreate
import com.stress.resolve.request.PostSearch
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class PostServiceTest {

    @Autowired
    private lateinit var postService: PostService

    @Autowired
    private lateinit var postRepository: PostRepository

    @BeforeEach
    fun cleanUp() {
        postRepository.deleteAll()
    }

    @Test
    fun `글을 작성한다`() {
        // given
        val request = PostCreate(title = "제목입니다.", content = "내용입니다.")

        // when
        postService.write(request)

        // then
        val post = postRepository.findAll()[0]
        assertThat(post.title).isEqualTo("제목입니다.")
        assertThat(post.content).isEqualTo("내용입니다.")
    }

    @Test
    fun `저장한 글을 조회한다`() {
        // given
        val save = postRepository.save(Post(title = "제목입니다.", content = "내용입니다."))

        // when
        val post = postService.get(save.id!!)

        // then
        assertNotNull(post)
        assertThat(postRepository.count()).isEqualTo(1L)
        assertThat(post.title).isEqualTo("제목입니다.")
        assertThat(post.content).isEqualTo("내용입니다.")
    }

    @Test
    fun `저장한 글 목록의 1페이지를 조회한다`() {
        // given
        postRepository.saveAll(List(30) { Post(title = "제목입니다 ${it + 1}", content = "내용입니다 ${it + 1}") })

        // when
        val posts = postService.getList(PostSearch(page = 1, size = 5))

        // then
        assertThat(posts.size).isEqualTo(5)
        assertThat(posts).extracting("title").containsExactly("제목입니다 30", "제목입니다 29", "제목입니다 28", "제목입니다 27", "제목입니다 26")
        assertThat(posts).extracting("content").containsExactly("내용입니다 30", "내용입니다 29", "내용입니다 28", "내용입니다 27", "내용입니다 26")
    }
}