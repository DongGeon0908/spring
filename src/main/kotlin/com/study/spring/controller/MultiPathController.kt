package com.study.spring.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 중복된 Path도 사용 가능
 *
 * Path가 같더라 조건이 다르면 선언 가능
 **/
@RestController
@RequestMapping("/api/v1/multi-path")
class MultiPathController {
    @GetMapping("/same-path", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun samePath1() = "ok"

    @GetMapping("/same-path", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun samePath2() = "ok"
}