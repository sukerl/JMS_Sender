package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import com.example.demo.config.JmsConfig;

@SpringBootApplication
public class JmsSenderApplication {

	public static void main(String[] args) {
		// Launch the application
		ConfigurableApplicationContext context = SpringApplication.run(JmsSenderApplication.class, args);

		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		jmsTemplate.setConnectionFactory();
		//jmsTemplate.setTimeToLive(60000);
		
		// Send a message with a POJO - the template reuse the message converter
		while(true) {
			System.out.println("Sending an email message.");
			jmsTemplate.convertAndSend("test.topic", new Email("info@example.com", "Hello"));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
