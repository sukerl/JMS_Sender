package com.example.demo.config;

import javax.jms.ConnectionFactory;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfig {

	/*
	@Bean(name = "topicJmsListenerContainerFactory")
	public DefaultJmsListenerContainerFactory getTopicFactory() {
		DefaultJmsListenerContainerFactory f = new DefaultJmsListenerContainerFactory();
		// f.setConnectionFactory(connectionFactory);
		f.setSessionTransacted(true);
		f.setPubSubDomain(true);
		return f;
	}
	*/

	@Bean
	public JmsListenerContainerFactory<?> topicFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// This provides all boot's default to this factory, including the message converter
		factory.setSessionTransacted(true);
		factory.setPubSubDomain(true);
		configurer.configure(factory, connectionFactory);
		// You could still override some of Boot's default if necessary.
		return factory;
	}
	
	@Bean
	public JmsListenerContainerFactory<?> queueFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// This provides all boot's default to this factory, including the message
		// converter
		configurer.configure(factory, connectionFactory);
		// You could still override some of Boot's default if necessary.
		return factory;
	}

	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
}

/*
 * @Configuration public class JmsConfig {
 * 
 * String BROKER_URL = "tcp://localhost:61616"; String BROKER_USERNAME =
 * "admin"; String BROKER_PASSWORD = "admin";
 * 
 * @Bean public ActiveMQConnectionFactory connectionFactory(){
 * ActiveMQConnectionFactory connectionFactory = new
 * ActiveMQConnectionFactory(); connectionFactory.setBrokerURL(BROKER_URL);
 * connectionFactory.setPassword(BROKER_USERNAME);
 * connectionFactory.setUserName(BROKER_PASSWORD); return connectionFactory; }
 * 
 * @Bean public JmsTemplate jmsTemplate(){ JmsTemplate template = new
 * JmsTemplate(); template.setConnectionFactory(connectionFactory()); return
 * template; }
 * 
 * @Bean public DefaultJmsListenerContainerFactory jmsListenerContainerFactory()
 * { DefaultJmsListenerContainerFactory factory = new
 * DefaultJmsListenerContainerFactory();
 * factory.setConnectionFactory(connectionFactory());
 * factory.setConcurrency("1-1"); return factory; }
 * 
 * }
 */

/*
@Configuration
@EnableJms
public class FooJmsConfig {

@Bean
public ActiveMQConnectionFactory connectionFactory() {
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    connectionFactory.setBrokerURL(BROKER_URL);
    connectionFactory.setPassword(BROKER_USERNAME);
    connectionFactory.setUserName(BROKER_PASSWORD);
    connectionFactory.setUseCompression(true);

    connectionFactory.setConnectionIDPrefix("DRR");
    connectionFactory.setUseAsyncSend(true);
    return connectionFactory;
}

@Bean(name= "foo1")
public DefaultJmsListenerContainerFactory foo1() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    factory.setConcurrency("1-1");
    factory.setPubSubDomain(true);
    factory.setSubscriptionDurable(true);

    connectionFactory.setClientID("FOO_1");
    return factory;
}

 @Bean(name= "foo2")
public DefaultJmsListenerContainerFactory foo2() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    factory.setConcurrency("1-1");
    factory.setPubSubDomain(true);
    factory.setSubscriptionDurable(true);

    connectionFactory.setClientID("FOO_1");
    return factory;
}

}

*/