package ping_pong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Dominik Ciborowski
 */
public class ClientWithKeyboardInput {

    public static void main(String[] args) throws IOException {
        Socket sck = new Socket(Client.HOST, Client.PORT);

        BufferedReader input = new BufferedReader(new InputStreamReader(sck.getInputStream()));
        PrintWriter output = new PrintWriter(sck.getOutputStream(), true);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter message for server:");

        output.println(scanner.nextLine());

        scanner.close();

        System.out.println("Got from server: " + input.readLine());
    }

}
