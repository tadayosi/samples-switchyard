package com.redhat.samples.switchyard.ws;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.annotation.WebServlet;

import org.apache.log4j.Logger;

@WebService(serviceName = "PingService")
@WebServlet("/soap/ping")
public class PingService {

    private static final Logger LOGGER = Logger.getLogger(PingService.class);

    @WebMethod
    public Date ping(@WebParam(name = "time") Date time) {
        LOGGER.info("ping = " + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(time));
        return new Date();
    }

}
