package com.example.demo;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

  @JmsListener(destination = "test.topic", containerFactory = "jmsTopicListenerContainerFactory")
  public void receiveTopicMessage(Email email) {
    System.out.println("Received from topic <" + email + ">");
  }
  
  @JmsListener(destination = "test.queue", containerFactory = "jmsQueueListenerContainerFactory")
  public void receiveQueueMessage(Email email) {
    System.out.println("Received from queue <" + email + ">");
  }

}