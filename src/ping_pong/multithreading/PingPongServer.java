package ping_pong.multithreading;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Dominik Ciborowski
 */
public class PingPongServer {

    // Connection listener - our server
    private ServerSocket serverSocket;

    public PingPongServer(String host, int port) throws IOException {
        serverSocket = new ServerSocket();

        // Bind server to specific host and number
        serverSocket.bind(new InetSocketAddress(host, port));
    }

    /**
     * Listen for connections and handle connected clients.
     *
     * @throws IOException
     */
    public void listenForConnections() throws IOException {
        boolean isRunning = true;

        while (isRunning) {
            Socket sck = serverSocket.accept();
            System.out.println("Incoming connection: " + sck.getInetAddress() + ", port: " + sck.getLocalPort());

            // starting new client thread
            new Thread(new ClientThread(sck)).start();
        }
    }

    public static void main(String[] args) throws IOException {
        PingPongServer pingPongServer = new PingPongServer("localhost", 9000);
        pingPongServer.listenForConnections();
    }
}
