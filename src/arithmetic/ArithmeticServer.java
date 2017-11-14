package arithmetic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple Java socket server for arithmetic operations.
 *
 * @author Dominik Ciborowski
 */
public class ArithmeticServer {

    private ServerSocket serverSocket;

    public ArithmeticServer(String host, int port) throws IOException {
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(host, port));
    }

    public void start() {
        boolean isRunning = true;

        while (isRunning) {

            try {
                Socket sck = serverSocket.accept();

                new Thread(new ClientOnServerThread(sck)).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ArithmeticServer arithmeticServer = new ArithmeticServer("localhost", 9001);
        arithmeticServer.start();
    }
}
