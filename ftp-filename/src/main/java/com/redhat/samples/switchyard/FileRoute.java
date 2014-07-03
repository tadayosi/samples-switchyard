package com.redhat.samples.switchyard;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class FileRoute extends RouteBuilder {

    public static final String OUT_FILE = "out.txt";

    public void configure() {
        // @formatter:off
        from("switchyard://FileService").routeId(getClass().getName())
                .log("********************************************************************************")
                .log("body = ${body}")
                .log("********************************************************************************")
                .transform().simple("<<< ${body} >>>")
                .setHeader(Exchange.FILE_NAME, constant(OUT_FILE))
                .to("switchyard://FTPOut");
        // @formatter:on
    }

}
