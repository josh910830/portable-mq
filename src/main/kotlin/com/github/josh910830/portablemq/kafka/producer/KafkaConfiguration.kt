package com.github.josh910830.portablemq.kafka.producer

import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate

@Configuration
class KafkaConfiguration(
    @Value("\${spring.kafka.producer.bootstrap-servers}")
    private val bootstrapServers: String = "localhost:9092"
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> {
        log.info("KafkaTemplate will produce to $bootstrapServers. Configure \${spring.kafka.producer.bootstrap-servers}")
        val configs = mapOf(
            BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
        )
        val producerFactory = DefaultKafkaProducerFactory<String, String>(configs)
        return KafkaTemplate(producerFactory)
    }

}
