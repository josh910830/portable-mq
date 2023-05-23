package com.github.josh910830.portablemq.support.autoconfiguration

import com.github.josh910830.portablemq.core.consumer.deadletter.interfaces.DeadletterNotifier
import com.github.josh910830.portablemq.core.consumer.deadletter.interfaces.DeadletterStore
import com.github.josh910830.portablemq.core.consumer.deadletter.interfaces.RedriveTokenManager
import com.github.josh910830.portablemq.core.consumer.log.interfaces.ConsumptionLogStore
import com.github.josh910830.portablemq.kafka.consumer.badletter.interfaces.BadletterNotifier
import com.github.josh910830.portablemq.kafka.consumer.badletter.interfaces.BadletterStore
import com.github.josh910830.portablemq.support.autoconfiguration.adapter.*
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Configuration
class DefaultBeanAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun portableMqExecutor(): Executor {
        val concurrency = 3
        return Executors.newFixedThreadPool(concurrency)
    }


    @Bean
    @ConditionalOnMissingBean
    fun memoryConsumptionLogStore(): ConsumptionLogStore {
        return MemoryConsumptionLogStore()
    }

    @Bean
    @ConditionalOnMissingBean
    fun memoryDeadletterStore(): DeadletterStore {
        return MemoryDeadletterStore()
    }

    @Bean
    @ConditionalOnMissingBean
    fun stubRedriveTokenManager(): RedriveTokenManager {
        return StubRedriveTokenManager()
    }

    @Bean
    @ConditionalOnMissingBean
    fun logDeadletterNotifier(): DeadletterNotifier {
        return LogDeadletterNotifier()
    }

    @Bean
    @ConditionalOnMissingBean
    fun memoryBadletterStore(): BadletterStore {
        return MemoryBadletterStore()
    }

    @Bean
    @ConditionalOnMissingBean
    fun logBadletterNotifier(): BadletterNotifier {
        return LogBadletterNotifier()
    }

}
