package com.redhat.samples.switchyard;

import javax.inject.Inject;

import org.apache.camel.component.http.HttpOperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.switchyard.Context;
import org.switchyard.Exchange;
import org.switchyard.HandlerException;
import org.switchyard.Message;
import org.switchyard.component.bean.Reference;
import org.switchyard.component.bean.ReferenceInvocation;
import org.switchyard.component.bean.ReferenceInvoker;
import org.switchyard.component.bean.Service;
import org.switchyard.component.http.composer.HttpComposition;
import org.switchyard.component.http.composer.HttpContextMapper;
import org.switchyard.component.http.composer.HttpRequestInfo;

@Service(value = ProxyService.class, name = "BeanProxyService")
public class ProxyBean implements ProxyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyBean.class);

    private static final String REFERENCE_BASE_URL = "http://localhost:18080";

    @Inject
    @Reference("TargetReference")
    private ReferenceInvoker target;

    @Inject
    private Exchange exchange;

    @Override
    public String process(String content) {
        Context msgContext = exchange.getMessage().getContext();
        HttpRequestInfo requestInfo = msgContext.getPropertyValue(HttpComposition.HTTP_REQUEST_INFO);
        LOGGER.info("********************************************************************************");
        LOGGER.info("method      = {}", requestInfo.getMethod());
        LOGGER.info("pathInfo    = {}", requestInfo.getPathInfo());
        LOGGER.info("queryString = {}", requestInfo.getQueryString());
        LOGGER.info("content     = {}", content);
        LOGGER.info("********************************************************************************");
        return invoke(requestInfo, content);
    }

    private String invoke(HttpRequestInfo requestInfo, String content) {
        int statusCode = 500;
        String replyContent = "";

        ReferenceInvocation invocation = null;
        try {
            invocation = target.newInvocation();
            map(requestInfo, invocation.getMessage().getContext());
            Message message = invocation.invoke(content).getMessage();
            replyContent = message.getContent(String.class);
            //Integer responseCode = message.getContext().getPropertyValue(org.apache.camel.Exchange.HTTP_RESPONSE_CODE);
            statusCode = 200;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            if (invocation != null) {
                statusCode = retrieveStatusCode(invocation);
            }
        }

        Message reply = exchange.createMessage();
        reply.setContent(replyContent);
        reply.getContext().setProperty(HttpContextMapper.HTTP_RESPONSE_STATUS, statusCode);
        exchange.send(reply);
        return replyContent;
    }

    private void map(HttpRequestInfo requestInfo, Context context) {
        context.setProperty(org.apache.camel.Exchange.HTTP_METHOD, requestInfo.getMethod());
        context.setProperty(org.apache.camel.Exchange.HTTP_URI, REFERENCE_BASE_URL + requestInfo.getPathInfo());
        context.setProperty(org.apache.camel.Exchange.HTTP_QUERY, requestInfo.getQueryString());
    }

    private int retrieveStatusCode(ReferenceInvocation invocation) {
        Exception exception = invocation.getContext().getPropertyValue(org.apache.camel.Exchange.EXCEPTION_CAUGHT);
        if (!(exception instanceof HandlerException)) { return 500; }

        HandlerException handlerEx = (HandlerException) exception;
        HttpOperationFailedException httpOperationFailed = (HttpOperationFailedException) handlerEx.getCause();
        return httpOperationFailed.getStatusCode();
    }

}
