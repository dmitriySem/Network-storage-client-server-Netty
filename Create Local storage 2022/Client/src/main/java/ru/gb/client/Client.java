package ru.gb.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.File;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                    new StringEncoder(),
                                    new StringDecoder(),
                                    new SimpleChannelInboundHandler<String>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                    System.out.println("Message from server: " + s);
                                }
                            });
                        }
                    });

            System.out.println("Client is started");
            Scanner scanner = new Scanner(System.in);

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();

            while (true) {
                System.out.println("Print message:");
                String message = scanner.nextLine();
                channelFuture.channel().writeAndFlush(message + System.lineSeparator());
            }


//            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
//            добавить логгер
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }


    }

}
