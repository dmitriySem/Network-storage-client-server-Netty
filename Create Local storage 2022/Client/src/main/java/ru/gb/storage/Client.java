package ru.gb.storage;

public class Client {
    public static void main(String[] args) throws Exception {
        System.out.println("Client");

        int port = 8080;
        new Server(port).run();

    }

}
