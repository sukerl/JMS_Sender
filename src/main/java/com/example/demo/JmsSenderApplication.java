package com.example.demo;

import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class JmsSenderApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(JmsSenderApplication.class, args);

		JmsTemplate jmsQueueTemplate = BeanFactoryAnnotationUtils.qualifiedBeanOfType(context.getBeanFactory(), JmsTemplate.class, "jmsQueueTemplate");
		jmsQueueTemplate.setExplicitQosEnabled(true);
		jmsQueueTemplate.setDeliveryPersistent(false);
		
		JmsTemplate jmsTopicTemplate = BeanFactoryAnnotationUtils.qualifiedBeanOfType(context.getBeanFactory(), JmsTemplate.class, "jmsTopicTemplate");
		jmsTopicTemplate.setExplicitQosEnabled(true);
		jmsTopicTemplate.setDeliveryPersistent(false);
		
		// Send a message with a POJO - the template reuse the message converter
		while(true) {
			System.out.println("Sending an email message.");
			jmsQueueTemplate.convertAndSend("test.queue", new Email("info.queue@example.com", "Hello Queue"));
			jmsTopicTemplate.convertAndSend("test.topic", new Email("info.topic@example.com", "Hello Topic"));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
