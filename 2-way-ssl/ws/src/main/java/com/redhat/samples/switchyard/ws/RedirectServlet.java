package com.redhat.samples.switchyard.ws;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet("/redirect")
@SuppressWarnings("serial")
public class RedirectServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RedirectServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("GET  - " + request.getRequestURI());
        redirect(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        LOGGER.info("POST - " + request.getRequestURI());
        redirect(request, response);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String type = request.getParameter("type");
        if (type == null || "soap".equals(type)) {
            String url = "soap/ping";
            if (request.getQueryString() != null)
                url += "?" + request.getQueryString();
            response.sendRedirect(url);
        }
    }

}
