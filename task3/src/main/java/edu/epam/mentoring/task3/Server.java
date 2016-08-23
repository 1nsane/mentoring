package edu.epam.mentoring.task3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

//    private static final Set<String> cache = new HashSet<>();

    private int port;
    private ServerSocket server;
    private Socket socket;
    private DataOutputStream os;
    private DataInputStream is;
    private ReentrantLock lock = new ReentrantLock();

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = new ServerSocket(port);
        socket = server.accept();
        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());

        while (true) {
            readMessage();
        }
    }

    private final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
    private ExecutorService service = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, queue);

    private void readMessage() throws IOException {
        int count = is.readInt();
        for (int i = 0; i < count; i++) {
            int length = is.readInt();
            byte[] bytes = new byte[length];
            is.readFully(bytes);
            int index = i;
            service.execute(() -> calcSentence(bytes, index));
        }
    }

    private void calcSentence(byte[] bytes, int index) {
//        String str = new String(bytes).intern();
        String str = new String(bytes);
        int big = 0;
        int small = 0;
        for (String word : str.split(" ")) {
            if (word.length() <= 4) {
//                cache.add(word);
                small++;
            } else {
                big++;
            }
        }
        synchronized (this) {
            try {
                os.writeInt(index);
                os.writeInt(big);
                os.writeInt(small);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
/*        try {
            lock.lock();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }*/
    }

    public static void main(String[] args) throws IOException {
        new Server(7890).start();
    }
}
