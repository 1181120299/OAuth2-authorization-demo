package com.jack.resourceserver.config;

import com.jack.resourceserver.annotation.ResourceRabbitmqBeanDefinitionRegistrar;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableRabbit
@Import(ResourceRabbitmqBeanDefinitionRegistrar.class)
public class ResourceRabbitConfig {

}
