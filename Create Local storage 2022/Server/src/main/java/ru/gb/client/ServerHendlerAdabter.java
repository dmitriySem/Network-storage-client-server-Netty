package ru.gb.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ServerHendlerAdabter extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Client is connected...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
     try {

        while (in.isReadable()){
            System.out.print("Message from client: " + in.readByte());
            System.out.flush();
        }

     } finally {
         ReferenceCountUtil.release(msg);
     }

        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        System.out.println("Client is disconect");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { //выполняется, когда выбрасывается исключения от Netty
        cause.printStackTrace();
        ctx.close();
    }
}
