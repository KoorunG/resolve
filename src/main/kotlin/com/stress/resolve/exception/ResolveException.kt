package com.stress.resolve.exception

/**
 * 공통 예외 클래스
 */
abstract class ResolveException(override val message: String, val code: Int = 400): RuntimeException(message)