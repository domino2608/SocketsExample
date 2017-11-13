package ping_pong.one_thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Dominik Ciborowski
 */
public class PingPongServer {

    // sluchacz polaczen - serwer nasluchujacy
    private ServerSocket serverSocket;

    public PingPongServer(String host, int port) throws IOException {
        serverSocket = new ServerSocket();

        // przypisywanie serwera do konkretnego adresu
        serverSocket.bind(new InetSocketAddress(host, port));
    }

    // nasluchuj polaczen
    // nasz serwer dziala na jednym watku i jest blokujacy
    //
    public void listenForConnections() throws IOException {
        boolean isRunning = true;

        // Petla nieskonczona - sluchanie polaczen klientow
        while (isRunning) {
            Socket sck = serverSocket.accept();
            System.out.println("Incoming connection: " + sck.getInetAddress() + ", port: " + sck.getLocalPort());

            handlePingPongConnection(sck);
            sck.close(); // troche naiwne ale tak mi bylo latwe
        }
    }

    // dzialanie na polaczeniu - niezbyt piekna obsluga bledow
    // normalnie nalezaloby zrobic zamykanie strumieni ;)
    private void handlePingPongConnection(Socket sck) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(sck.getInputStream()));
        PrintWriter output = new PrintWriter(sck.getOutputStream(), true);

        String fromClient = input.readLine();
        output.println(fromClient.toUpperCase());
    }

    // main do odpalania serwera
    public static void main(String[] args) throws IOException {
        PingPongServer pingPongServer = new PingPongServer("localhost", 9000);
        pingPongServer.listenForConnections();
    }
}
