import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private final static ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        try {
            new Client().start();
        } finally {
            THREAD_POOL.shutdown();
        }
    }

    public void start() {
        try {
            SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 9000));
            while (true) {
                Scanner scanner = new Scanner(System.in);
                ByteBuffer buffer = ByteBuffer.allocate(256);

                System.out.println("Enter message to server: ");
                String output = scanner.nextLine();

                buffer.put(output.getBytes());
                buffer.flip();
                channel.write(buffer);
                System.out.println("Message is send! ");

                channel.read(buffer);
                String message = new String(buffer.array());
                System.out.println("New message from server: " + message);

            }
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }
}
