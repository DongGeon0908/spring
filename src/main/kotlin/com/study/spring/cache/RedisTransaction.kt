package com.study.spring.cache

import org.springframework.dao.DataAccessException
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SessionCallback

/**
 * Redis Transaction
 * @see <a href = "https://redis.io/docs/manual/transactions/">Redis Transaction Docs</a>
 **/
class RedisTransaction(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    fun transaction(command: (operation: RedisOperations<String, Any>) -> Unit) {
        redisTemplate.execute(object : SessionCallback<Void?> {
            @Throws(DataAccessException::class)
            override fun <K, V> execute(callbackOperations: RedisOperations<K, V>): Void? {
                callbackOperations.multi()

                /**
                 * invoke command service
                 **/
                command.invoke(redisTemplate)

                callbackOperations.exec()
                return null
            }
        })
    }
}
