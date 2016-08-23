package edu.epam.mentoring.task3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

//    private static final Set<String> cache = new HashSet<>();

    private int port;
    private ServerSocket server;
    private Socket socket;
    private DataOutputStream os;
    private DataInputStream is;
    private ReentrantLock lock = new ReentrantLock();
    private BlockingQueue<Runnable> tasks = new ArrayBlockingQueue<>(10);
    private ExecutorService service = Executors.newFixedThreadPool(5);

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = new ServerSocket(port);
        socket = server.accept();
        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());

        for (int i = 0; i < 5; i++) {
            service.execute(() -> {
                while (true) {
                    try {
                        Runnable task = tasks.take();
                        task.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        while (true) {
            readMessage();
        }
    }

    private void readMessage() throws IOException {
        int count = is.readInt();
        for (int i = 0; i < count; i++) {
            int length = is.readInt();
            byte[] bytes = new byte[length];
            is.readFully(bytes);
            int index = i;
            try {
                tasks.put(() -> calcSentence(bytes, index));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
       /* synchronized (this) {
            try {
                os.writeInt(index);
                os.writeInt(big);
                os.writeInt(small);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        try {
            lock.lock();
            os.writeInt(index);
            os.writeInt(big);
            os.writeInt(small);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(7890).start();
    }
}
