package com.redhat.samples.switchyard;

import static org.switchyard.bus.camel.processors.Processors.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.switchyard.bus.camel.audit.Audit;
import org.switchyard.bus.camel.audit.Auditor;
import org.switchyard.bus.camel.processors.Processors;
import org.switchyard.metadata.ExchangeContract;
import org.switchyard.security.context.SecurityContext;

@Audit(PROVIDER_CALLBACK)
@Named
public class SampleAuditor implements Auditor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleAuditor.class);

    @Override
    public void beforeCall(Processors processor, Exchange exchange) {
        log("Before", processor, exchange);
    }

    @Override
    public void afterCall(Processors processor, Exchange exchange) {
        log("After", processor, exchange);
    }

    private void log(String tag, Processors processor, Exchange exchange) {
        LOGGER.info("================================================================================");
        LOGGER.info("{}: {}", tag, processor);

        // Context
        LOGGER.info("[Context properties]");
        for (String key : sort(exchange.getContext().getProperties().keySet())) {
            LOGGER.info("    {} = {}", key, exchange.getContext().getProperty(key));
        }

        // Exchange
        LOGGER.info("[Exchange properties]");
        for (String key : sort(exchange.getProperties().keySet())) {
            if ("CamelMessageHistory".equals(key)) {
                LOGGER.info("    {} =", key);
                @SuppressWarnings("unchecked")
                List<MessageHistory> history = exchange.getProperty(key, List.class);
                for (MessageHistory h : history) {
                    LOGGER.info("      " + h);
                }
            } else if ("org.switchyard.bus.camel.contract".equals(key)) {
                LOGGER.info("    {} =", key);
                ExchangeContract contract = exchange.getProperty(key, ExchangeContract.class);
                LOGGER.info("      consumer op = " + contract.getConsumerOperation());
                LOGGER.info("      provider op = " + contract.getProviderOperation());
            } else if (SecurityContext.class.getName().equals(key)) {
                LOGGER.info("    {} =", key);
                SecurityContext sc = (SecurityContext) exchange.getProperty(key);
                LOGGER.info("      " + sc.getCredentials());
            } else {
                LOGGER.info("    {} = {}", key, exchange.getProperty(key));
            }
        }

        // In
        LOGGER.info("[In message headers]");
        for (String key : sort(exchange.getIn().getHeaders().keySet())) {
            LOGGER.info("    {} = {}", key, exchange.getIn().getHeader(key));
        }

        // Out
        LOGGER.info("[Out message headers]");
        if (exchange.hasOut()) {
            for (String key : sort(exchange.getOut().getHeaders().keySet())) {
                LOGGER.info("    {} = {}", key, exchange.getOut().getHeader(key));
            }
        }

        LOGGER.info("================================================================================");
    }

    private static List<String> sort(Set<String> set) {
        List<String> list = new ArrayList<>(set);
        Collections.sort(list);
        return list;
    }

}
