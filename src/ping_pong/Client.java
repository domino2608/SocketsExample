package ping_pong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Dominik Ciborowski
 */
public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = 9000;

    private static final String TO_SEND = "test_message";


    public static void main(String[] args) throws IOException {
        Socket sck = new Socket("localhost", 9000);

        BufferedReader input = new BufferedReader(new InputStreamReader(sck.getInputStream()));
        PrintWriter output = new PrintWriter(sck.getOutputStream(), true);

        output.println(TO_SEND);
        System.out.println("Got from server: " + input.readLine());
    }


}
