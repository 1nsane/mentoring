package edu.epam.mentoring.task3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    private static final int POOL_SIZE = 5;
    private static final int BYTES_PER_THREAD = 2000;
    private static final int BUFFER_SIZE = POOL_SIZE * BYTES_PER_THREAD;

    private final byte[] bytes = new byte[BUFFER_SIZE];

    private int port;
    private ServerSocket server;
    private Socket socket;
    private DataOutputStream os;
    private DataInputStream is;
//    private ReentrantLock lock = new ReentrantLock();
    private BlockingQueue<Integer> tasks = new ArrayBlockingQueue<>(POOL_SIZE);
    private BlockingQueue<Integer> freeOffsets = new ArrayBlockingQueue<>(POOL_SIZE);
    private ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        for (int i = 0; i < POOL_SIZE; i++) {
            freeOffsets.add(i * BYTES_PER_THREAD);
        }

        server = new ServerSocket(port);
        socket = server.accept();
        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());

        for (int i = 0; i < POOL_SIZE; i++) {
            service.execute(() -> {
                while (true) {
                    try {
                        Integer offset = tasks.take();
                        calcSentence(offset);
                        freeOffsets.put(offset);
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
            try {
                int length = is.readInt();
                Integer offset = freeOffsets.take();
                is.readFully(bytes, offset, length);
                tasks.put(offset);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void calcSentence(int offset) {
        int big = 0;
        int small = 0;

        int wordLength = 0;
        for (int i = offset; i < offset + BYTES_PER_THREAD; i++) {
            if (bytes[i] == 32) { //space character
                if (wordLength > 0) {
                    if (wordLength > 4) {
                        big++;
                    } else {
                        small++;
                    }
                    wordLength = 0;
                }
            } else {
                wordLength++;
            }
        }

//        try {
//            lock.lock();
//            os.writeInt(index);
//            os.writeInt(big);
//            os.writeInt(small);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
    }

    public static void main(String[] args) throws IOException {
        new Server(7890).start();
    }
}
