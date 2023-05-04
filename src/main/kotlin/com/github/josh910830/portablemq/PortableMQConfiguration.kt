package com.github.josh910830.portablemq

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync

@Configuration
@EnableAsync
@ComponentScan
@ConfigurationPropertiesScan
class PortableMQConfiguration
