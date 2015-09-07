package com.redhat.samples.switchyard;

import javax.inject.Named;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Sharable
@Named
public class ByteArrayEncoder extends OneToOneEncoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ByteArrayEncoder.class);

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) {
        LOGGER.info("============================================================");
        LOGGER.info("message = {}", msg);
        LOGGER.info("============================================================");
        if (msg instanceof byte[]) {
            return ChannelBuffers.wrappedBuffer((byte[]) msg);
        } else {
            return msg;
        }
    }

}
