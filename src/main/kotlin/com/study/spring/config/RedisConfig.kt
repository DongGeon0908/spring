package com.study.spring.config

import com.study.spring.cache.RedisTransaction
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer


@EnableCaching
@Configuration
@EnableConfigurationProperties(RedisProperties::class)
class RedisConfig(
    private val properties: RedisProperties
) : CachingConfigurerSupport() {
    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory::class)
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(properties.host, properties.port)
    }

    @Bean
    @ConditionalOnClass(RedisConnectionFactory::class)
    @ConditionalOnMissingBean(RedisTemplate::class)
    fun redisTemplate(): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>()
            .apply {
                this.setConnectionFactory(redisConnectionFactory())
                this.keySerializer = StringRedisSerializer()
                this.valueSerializer = GenericJackson2JsonRedisSerializer()
                this.hashKeySerializer = StringRedisSerializer()
                this.hashValueSerializer = GenericJackson2JsonRedisSerializer()
            }
    }

    @Bean
    @ConditionalOnBean(name = ["redisTemplate"])
    @ConditionalOnMissingBean(RedisTransaction::class)
    fun redisTransaction(): RedisTransaction {
        return RedisTransaction(redisTemplate())
    }
}
