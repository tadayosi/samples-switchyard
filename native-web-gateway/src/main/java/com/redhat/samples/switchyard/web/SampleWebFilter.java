package com.redhat.samples.switchyard.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter("/*")
public class SampleWebFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleWebFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        LOGGER.info("doFilter");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        LOGGER.info("destroy");
    }

}
