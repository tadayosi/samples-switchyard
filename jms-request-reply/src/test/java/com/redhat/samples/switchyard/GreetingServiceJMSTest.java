package com.redhat.samples.switchyard;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.component.test.mixins.hornetq.HornetQMixIn;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(
        mixins = { CDIMixIn.class, HornetQMixIn.class },
        config = SwitchYardTestCaseConfig.SWITCHYARD_XML)
public class GreetingServiceJMSTest {

    private HornetQMixIn hornetQMixIn;
    private Session session;

    @Before
    public void setUp() {
        session = hornetQMixIn.createJMSSession();
    }

    @After
    public void tearDown() {
        HornetQMixIn.closeJMSSession(session);
    }

    @Test
    public void hello() throws JMSException {
        test(Constants.QUEUE_HELLO_REQUEST, Constants.QUEUE_HELLO_REPLY, getClass().getSimpleName(), "Hello, %s!");
    }

    @Test
    public void goodbye() throws JMSException {
        test(Constants.QUEUE_GOODBYE_REQUEST, Constants.QUEUE_GOODBYE_REPLY, getClass().getSimpleName(), "Goodbye, %s!");
    }

    private void test(String requestQueue, String replyQueue, String name, String expectedFormat) throws JMSException {
        MessageConsumer consumer = session.createConsumer(HornetQMixIn.getJMSQueue(replyQueue));
        MessageProducer producer = session.createProducer(HornetQMixIn.getJMSQueue(requestQueue));
        Message message = hornetQMixIn.createJMSMessage(name);
        producer.send(message);

        Message reply = consumer.receive(3000);
        assertThat(reply, is(instanceOf(TextMessage.class)));
        assertThat(((TextMessage) reply).getText(), is(String.format(expectedFormat, name)));
    }

}
