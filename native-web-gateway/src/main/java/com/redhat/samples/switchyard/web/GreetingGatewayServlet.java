package com.redhat.samples.switchyard.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.switchyard.component.bean.Reference;

import com.redhat.samples.switchyard.GreetingService;

@SuppressWarnings("serial")
@WebServlet("/servlet")
@Named
public class GreetingGatewayServlet extends HttpServlet {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingGatewayServlet.class);
    
    @Inject
    @Reference
    private GreetingService service;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("op");
        String name = request.getParameter("name");
        String message = invoke(operation, name);
        LOGGER.info(message);
        writeTo(response, message);
    }
    
    private String invoke(String operation, String name) {
        String message = null;
        switch (operation) {
        case "hello":
            message = service.hello(name);
            break;
        case "goodbye":
            message = service.goodbye(name);
            break;
        default:
            break;
        }
        return message;
    }
    
    private void writeTo(HttpServletResponse response, String message) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(message);
        writer.close();
    }
    
}
