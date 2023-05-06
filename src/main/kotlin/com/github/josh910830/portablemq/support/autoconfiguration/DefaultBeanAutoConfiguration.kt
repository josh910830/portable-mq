package com.github.josh910830.portablemq.support.autoconfiguration

import com.github.josh910830.portablemq.core.consumer.deadletter.DeadletterNotifier
import com.github.josh910830.portablemq.core.consumer.deadletter.DeadletterStore
import com.github.josh910830.portablemq.core.consumer.deadletter.RedriveTokenManager
import com.github.josh910830.portablemq.core.consumer.log.ConsumptionLogStore
import com.github.josh910830.portablemq.kafka.consumer.badletter.BadletterNotifier
import com.github.josh910830.portablemq.kafka.consumer.badletter.BadletterStore
import com.github.josh910830.portablemq.support.autoconfiguration.log.LogBadletterNotifier
import com.github.josh910830.portablemq.support.autoconfiguration.log.LogDeadletterNotifier
import com.github.josh910830.portablemq.support.autoconfiguration.memory.MemoryBadletterStore
import com.github.josh910830.portablemq.support.autoconfiguration.memory.MemoryConsumptionLogStore
import com.github.josh910830.portablemq.support.autoconfiguration.memory.MemoryDeadletterStore
import com.github.josh910830.portablemq.support.autoconfiguration.stub.StubRedriveTokenManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DefaultBeanAutoConfiguration {

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
