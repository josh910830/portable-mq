package com.github.josh910830.portablemq.support.autoconfiguration

import com.github.josh910830.portablemq.core.consumer.deadletter.interfaces.DeadletterNotifier
import com.github.josh910830.portablemq.core.consumer.deadletter.interfaces.DeadletterStore
import com.github.josh910830.portablemq.core.consumer.deadletter.interfaces.RedriveTokenManager
import com.github.josh910830.portablemq.core.consumer.log.interfaces.ConsumptionLogStore
import com.github.josh910830.portablemq.kafka.consumer.badletter.interfaces.BadletterNotifier
import com.github.josh910830.portablemq.kafka.consumer.badletter.interfaces.BadletterStore
import com.github.josh910830.portablemq.support.autoconfiguration.adapter.log.LogBadletterNotifier
import com.github.josh910830.portablemq.support.autoconfiguration.adapter.log.LogDeadletterNotifier
import com.github.josh910830.portablemq.support.autoconfiguration.adapter.memory.MemoryBadletterStore
import com.github.josh910830.portablemq.support.autoconfiguration.adapter.memory.MemoryConsumptionLogStore
import com.github.josh910830.portablemq.support.autoconfiguration.adapter.memory.MemoryDeadletterStore
import com.github.josh910830.portablemq.support.autoconfiguration.adapter.stub.StubRedriveTokenManager
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
