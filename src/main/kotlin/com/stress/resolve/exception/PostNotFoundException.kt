package com.stress.resolve.exception

private const val MESSAGE = "존재하지 않는 글입니다!"
private const val CODE = 404
class PostNotFoundException : ResolveException(MESSAGE, CODE)