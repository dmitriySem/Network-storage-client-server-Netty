package ru.gb.client;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    private int port;


    public Server(int port) {
        this.port = port;
    }

    public void run() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(); //прием входящих клиентов
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(); //обрабатывает потоки данных

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap(); //позволяет настроить сервер
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class) //создается канал NioServerSocketChannel после того как, приянто входящее соединение
                    .childHandler(new ChannelInitializer<SocketChannel>() { // обработчик, кот будем использовать для открытого канала.
                                                                            // ChannelInitializer помогает пользователю сконфигурировать новый канал.
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new ServerHendler());
                        }
                    }).option(ChannelOption.SO_BACKLOG,128) //параметры канала, применяется к NioServerSocketChannel, который применяет вход. соед
                    .childOption(ChannelOption.SO_KEEPALIVE, true); //применяется к оброба-м каналам

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync(); //принимаем вход. сообщение.

            System.out.println("Server started");

            channelFuture.channel().closeFuture().sync(); //закрываетм сервер

        } catch (InterruptedException e) {
//            Добавить логгирование
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args)  {
            new Server(8080).run();


    }
}
