package arithmetic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Dominik Ciborowski
 */
public class ArithmeticClient {

    public static void main(String[] args) throws IOException {
        Socket sck = new Socket("localhost", 9001);

        BufferedReader input = new BufferedReader(new InputStreamReader(sck.getInputStream()));
        PrintWriter output = new PrintWriter(sck.getOutputStream(), true);

        output.println(Protocol.constructMessage(Protocol.ADD, 2.0, 1.0));
        System.out.println("Got from server: " + input.readLine());

        output.println(Protocol.constructMessage(Protocol.SUBSTR, 10.0, 1.0));
        System.out.println("Got from server: " + input.readLine());

        output.println(Protocol.constructMessage(Protocol.MULT, 2.0, 15.0));
        System.out.println("Got from server: " + input.readLine());

        output.println(Protocol.constructMessage(Protocol.DIV, 6.0, 3.0));
        System.out.println("Got from server: " + input.readLine());

        output.println(Protocol.constructMessage(Protocol.SQRT, 5.0));
        System.out.println("Got from server: " + input.readLine());
    }

}
