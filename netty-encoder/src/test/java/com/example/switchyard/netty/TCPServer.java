package com.example.switchyard.netty;

import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.ServerSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TCPServer.class);

    private final ServerSocketChannelFactory factory;
    private final ServerBootstrap bootstrap;
    private final ChannelGroup channels;
    private final EchoHandler handler;
    private final int port;

    public TCPServer(int port) {
        this.port = port;
        channels = new DefaultChannelGroup();
        handler = new EchoHandler();
        factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        bootstrap = new ServerBootstrap(factory);
        bootstrap.setPipeline(Channels.pipeline(handler));
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
    }

    public void start() {
        Channel c = bootstrap.bind(new InetSocketAddress(port));
        channels.add(c);
    }

    public void stop() {
        ChannelGroupFuture f = channels.close();
        f.awaitUninterruptibly();
        factory.releaseExternalResources();
    }

    public byte[] receivedMessage() {
        return handler.received;
    }

    private class EchoHandler extends SimpleChannelHandler {

        private byte[] received = null;

        @Override
        public void channelOpen(ChannelHandlerContext c, ChannelStateEvent e) throws Exception {
            channels.add(e.getChannel());
        }

        @Override
        public void messageReceived(ChannelHandlerContext c, MessageEvent e) throws Exception {
            ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
            e.getChannel().write(buffer);
            received = buffer.array();
            LOGGER.info("Size: {} bytes", received.length);
            StringWriter out = new StringWriter();
            while (buffer.readable())
                out.append((char) buffer.readByte());
            LOGGER.info(out.toString());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext c, ExceptionEvent e) throws Exception {
            e.getCause().printStackTrace();
            e.getChannel().close();
        }

    }

}
