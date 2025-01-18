package com.stress.resolve.controller

import com.stress.resolve.request.PostCreate
import com.stress.resolve.response.PostResponse
import com.stress.resolve.service.PostService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class PostController(
    private val postService: PostService
) {

    /**
     * [[ POST요청의 리턴값 ]]
     *
     * 1. 기본적으로 없다. (상태코드만 200, 201 정도로만 내려줌)
     *  => 이 경우 클라이언트는 필요할 때 리소스를 다시 GET 요청해야 함
     * 2. 클라이언트의 별도 요청 시 데이터의 일부를 전달해주기도 한다. (PK만, 또는 DTO)
     *  => 클라이언트에서 리소스의 상태를 "즉시" 확인해야 한다면 리턴값을 넘겨준다.
     */
    @PostMapping("/posts")
    fun post(@RequestBody @Valid request: PostCreate) {
        postService.write(request)
    }

    @GetMapping("/posts/{postId}")
    fun get(@PathVariable(value = "postId") id: Long): PostResponse =
        postService.get(id)
}