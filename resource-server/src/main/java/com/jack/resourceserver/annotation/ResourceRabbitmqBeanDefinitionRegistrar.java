package com.jack.resourceserver.annotation;

import com.jack.clientauthority.config.ClientRabbitConfig;
import com.jack.resourceserver.config.ResourceRabbitmqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Collections;
import java.util.UUID;

public class ResourceRabbitmqBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ResourceRabbitmqConstant[] rabbitmqConstants = ResourceRabbitmqConstant.values();
        for (ResourceRabbitmqConstant rabbitmqConstant : rabbitmqConstants) {
            GenericBeanDefinition queueBeanDefinition = new GenericBeanDefinition();
            queueBeanDefinition.setBeanClass(Queue.class);
            ConstructorArgumentValues queueConstructorArgumentValues = queueBeanDefinition.getConstructorArgumentValues();
            queueConstructorArgumentValues.addIndexedArgumentValue(0, rabbitmqConstant.queue);
            queueConstructorArgumentValues.addIndexedArgumentValue(1, true);
            queueConstructorArgumentValues.addIndexedArgumentValue(2, false);
            queueConstructorArgumentValues.addIndexedArgumentValue(3, false);
            queueConstructorArgumentValues.addIndexedArgumentValue(4, ClientRabbitConfig.assembleDeadQueueArgs());
            registry.registerBeanDefinition(generateBeanName("jack-rabbitmq-queue-" + rabbitmqConstant.queue), queueBeanDefinition);

            GenericBeanDefinition exchangeBeanDefinition = new GenericBeanDefinition();
            exchangeBeanDefinition.setBeanClass(DirectExchange.class);
            ConstructorArgumentValues exchangeConstructorArgumentValues = exchangeBeanDefinition.getConstructorArgumentValues();
            exchangeConstructorArgumentValues.addIndexedArgumentValue(0, rabbitmqConstant.exchange);
            registry.registerBeanDefinition(generateBeanName("jack-rabbitmq-exchange-" + rabbitmqConstant.exchange), exchangeBeanDefinition);

            GenericBeanDefinition bindBeanDefinition = new GenericBeanDefinition();
            bindBeanDefinition.setBeanClass(Binding.class);
            ConstructorArgumentValues bindConstructorArgumentValues = bindBeanDefinition.getConstructorArgumentValues();
            bindConstructorArgumentValues.addIndexedArgumentValue(0, rabbitmqConstant.queue);
            bindConstructorArgumentValues.addIndexedArgumentValue(1, Binding.DestinationType.QUEUE);
            bindConstructorArgumentValues.addIndexedArgumentValue(2, rabbitmqConstant.exchange);
            bindConstructorArgumentValues.addIndexedArgumentValue(3, rabbitmqConstant.routeKey);
            bindConstructorArgumentValues.addIndexedArgumentValue(4, Collections.emptyMap());
            registry.registerBeanDefinition(generateBeanName("jack-rabbitmq-bind"), bindBeanDefinition);
        }
    }

    private String generateBeanName(String name) {
        return name + UUID.randomUUID().toString().replaceAll("-", "");
    }
}
