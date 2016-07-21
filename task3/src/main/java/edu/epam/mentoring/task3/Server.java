package edu.epam.mentoring.task3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {

    private static final Set<String> cache = new HashSet<>();

    private int port;
    private ServerSocket server;
    private Socket socket;
    private DataOutputStream os;
    private DataInputStream is;

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

    private void readMessage() throws IOException {
        int count = is.readInt();
        for (int i = 0; i < count; i++) {
            int length = is.readInt();
            byte[] bytes = new byte[length];
            is.readFully(bytes);
            new Thread(() -> calcSentence(bytes)).start();
        }
    }

    private void calcSentence(byte[] bytes) {
        String str = new String(bytes).intern();
        for (String word : str.split(" ")) {
            if (word.length() < 4) {
                cache.add(word);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(7890).start();
    }
}
