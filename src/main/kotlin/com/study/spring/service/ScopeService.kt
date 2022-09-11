package com.study.spring.service

import mu.KotlinLogging
import org.springframework.stereotype.Service

/**
 * Test Scope Function
 **/
@Service
class ScopeService {
    private val logger = KotlinLogging.logger {}

    /**
     * 수신 객체의 내부 프로퍼티 변경후 반환
     **/
    fun applyScope() {
        val scope = ScopeDto(
            name = "김동건",
            age = 26
        )

        logger.info { """name : ${scope.name} | age : ${scope.age}""" }

        val response = scope.apply {
            name = "goofy"
        }

        logger.info { """name : ${response.name} | age : ${response.age}""" }
    }

    /**
     * 수신 객체의 내부 프로퍼티 변경후 마지막 라인을 반환
     **/
    fun runScope() {
        val scope = ScopeDto(
            name = "김동건",
            age = 26
        )

        logger.info { """name : ${scope.name} | age : ${scope.age}""" }

        val response = scope.run {
            name = "goofy"
            hello()
        }

        logger.info { response }
    }

    /**
     * 수신 객체의 내부 프로퍼티 변경후 마지막 라인을 반환
     * - run과 비슷하지만, with의 경우 parameter로 수신객체를 받음
     **/
    fun withScope() {
        val scope = ScopeDto(
            name = "김동건",
            age = 26
        )

        logger.info { """name : ${scope.name} | age : ${scope.age}""" }

        val response = with(scope) {
            name = "goofy"
            hello()
        }

        logger.info { response }
    }

    /**
     * 수신 객체의 내부 프로퍼티 변경후 반환
     *
     * - nullable한 상황에서 유연하게 대처 가능
     **/
    fun letScope() {
        val scope: ScopeDto? = null

        val response = scope?.let {
            it.name = "goofy"
        }
    }

    /**
     * 수신 객체의 내부 프로퍼티를 변경할 때 사용
     **/
    fun alsoScope() {
        val scope = ScopeDto(
            name = "김동건",
            age = 26
        )

        logger.info { """name : ${scope.name} | age : ${scope.age}""" }

        val response = scope.also {
            it.name = "goofy"
        }

        logger.info { """name : ${response.name} | age : ${response.age}""" }
    }
}

data class ScopeDto(
    var name: String,
    var age: Int
) {
    fun hello(): String {
        return "${this.name} hello world"
    }
}
