package com.redhat.samples.switchyard;

import javax.inject.Inject;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ClientResponseFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.switchyard.Exchange;
import org.switchyard.Message;
import org.switchyard.component.bean.Reference;
import org.switchyard.component.bean.ReferenceInvocation;
import org.switchyard.component.bean.ReferenceInvoker;
import org.switchyard.component.bean.Service;
import org.switchyard.component.resteasy.composer.RESTEasyContextMapper;

@Service(value = GreetingService.class, name = "GreetingBeanProxy")
public class GreetingProxyBean implements GreetingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingProxyBean.class);

    @Inject
    @Reference("GreetingService")
    private ReferenceInvoker greeting;

    @Inject
    private Exchange exchange;

    @Override
    public String hello(String name) {
        LOGGER.info("********************************************************************************");
        LOGGER.info("name = {}", name);
        LOGGER.info("********************************************************************************");
        return invoke("hello", name);
    }

    @Override
    public String goodbye(String name) {
        LOGGER.info("********************************************************************************");
        LOGGER.info("name = {}", name);
        LOGGER.info("********************************************************************************");
        return invoke("goodbye", name);
    }

    private String invoke(String operation, Object content) {
        int statusCode = 500;
        ClientResponse<?> response = null;

        ReferenceInvocation invocation = null;
        try {
            invocation = greeting.newInvocation(operation);
            Message message = invocation.invoke(content).getMessage();
            response = message.getContent(ClientResponse.class);
            statusCode = response.getStatus();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            // TODO this won't work due to SWITCHYARD-1890
            if (e.getCause() instanceof ClientResponseFailure) {
                response = ((ClientResponseFailure) e.getCause()).getResponse();
                statusCode = response.getStatus();
            }
        }

        String replyContent = response != null ? response.getEntity(String.class) : "";
        sendReply(statusCode, replyContent);
        return replyContent;
    }

    private void sendReply(int statusCode, String content) {
        Message replyMessage = exchange.createMessage();
        replyMessage.setContent(content);
        replyMessage.getContext().setProperty(RESTEasyContextMapper.HTTP_RESPONSE_STATUS, statusCode);
        exchange.send(replyMessage);
    }

}
