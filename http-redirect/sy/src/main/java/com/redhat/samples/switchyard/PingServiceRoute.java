package com.redhat.samples.switchyard;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.builder.RouteBuilder;

public class PingServiceRoute extends RouteBuilder {

    public void configure() {
        //@formatter:off
        from("switchyard://PingService").routeId(getClass().getName())
            .setBody().simple(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date()))
            .log("ping = ${body}")
            .choice()
                .when(header("bean"))
                    .to("switchyard://RedirectSoapPingService")
                .otherwise()
                    .to("switchyard://SoapPingService")
            .end()
            .log("pong = ${body}");
        //@formatter:on
    }

}
