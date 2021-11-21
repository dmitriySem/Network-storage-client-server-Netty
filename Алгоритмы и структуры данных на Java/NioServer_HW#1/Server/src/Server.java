import java.io.IOException;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

    public static void main(String[] args) throws IOException {
        new Server().start();
    }

    public void start() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open(); //создание канала сервера
        serverSocket.socket().bind(new InetSocketAddress("localhost", 9000)); //сервер сокет слушает 9000 порт
        serverSocket.configureBlocking(false);//не блокирующий сокет, используем подход один сокет на несколько селекторов
        serverSocket.register(selector,SelectionKey.OP_ACCEPT); //все сообщения из ServerSocketChannel
        // с флагом OP_ACCEPT будет обрабатываться данным селектором.
        //OP_ACCEPT == новое подключение
        //OP_READ == готов к чтению
        //OP_WRITE == готов к записи
        //OP_CONNECT == совершено успешное подключение

        System.out.println("Server started");

        while (true){
            selector.select(); //ждем пока селектор получит сообщение.
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()){ // если ключ сообщений OP_ACCEPT
                    System.out.println("New selector acceptable event");
                    register(selector, serverSocket);
                }

                if (selectionKey.isReadable()) { //если ключ сообщений OP_READ
                    System.out.println("New selector readable event");
                    readMessage(selectionKey);
                }
                iterator.remove();
            }
        }
    }

    public void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {

        SocketChannel client = serverSocket.accept(); //получаем клиенский сокет
        client.configureBlocking(false); //не блокирует
        client.register(selector, SelectionKey.OP_READ); //для всех сообщений OP_READ перепрофилируем под них селектор,
        // который ранее занимался обработкой новых сообщений
        System.out.println("New client is connected");
    }

    public void readMessage(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(256); //заводим буффер с размером 256 байт
        client.read(byteBuffer);//читаем входящее сообщение в буффер
        String message = new String(byteBuffer.array());

        System.out.println("New message from client: " + message);
        client.write(byteBuffer);
//        System.out.println("Message send");

        byteBuffer.clear();
        client.close();
        key.cancel();
    }
}
