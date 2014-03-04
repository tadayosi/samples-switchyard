package com.redhat.samples.switchyard;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.switchyard.component.test.mixins.hornetq.HornetQMixIn;

public class GreetingClient {

    public static void main(final String[] args) throws Exception {
        String name = GreetingClient.class.getSimpleName();
        HornetQMixIn hornetQMixIn = new HornetQMixIn(false).setUser(Constants.USER).setPassword(Constants.PASSWORD);
        hornetQMixIn.initialize();
        try {
            requestReply(hornetQMixIn, Constants.QUEUE_HELLO_REQUEST, Constants.QUEUE_HELLO_REPLY, name);
            requestReply(hornetQMixIn, Constants.QUEUE_GOODBYE_REQUEST, Constants.QUEUE_GOODBYE_REPLY, name);
        } finally {
            hornetQMixIn.uninitialize();
        }
    }

    private static void requestReply(HornetQMixIn mixIn, String requestQueue, String replyQueue, String name)
            throws JMSException {
        Session session = mixIn.getJMSSession();
        MessageConsumer consumer = session.createConsumer(HornetQMixIn.getJMSQueue(replyQueue));
        MessageProducer producer = session.createProducer(HornetQMixIn.getJMSQueue(requestQueue));
        Message message = mixIn.createJMSMessage(name);
        producer.send(message);

        Message reply = consumer.receive();
        System.out.println(((TextMessage) reply).getText());
    }

}
