package ping_pong.multithreading;

import java.io.*;
import java.net.Socket;

/**
 * @author Dominik Ciborowski
 */
public class ClientThread implements Runnable, Closeable {

    private Socket sck;

    private BufferedReader input;

    private PrintWriter output;

    public ClientThread(Socket sck) throws IOException {
        this.sck = sck;

        initStreams();
    }

    private void initStreams() throws IOException {
        input = new BufferedReader(new InputStreamReader(sck.getInputStream()));

        output = new PrintWriter(sck.getOutputStream(), true);
    }

    @Override
    public void run() {

        try {
            String fromClient = input.readLine();

            output.println(fromClient.toUpperCase());

        } catch (IOException e) {

            e.printStackTrace();
        } finally {

            closeQuietly();
        }

    }

    @Override
    public void close() throws IOException {
        if (output != null) {
            output.close();
        }

        if (input != null) {
            input.close();
        }

        if (sck != null) {
            sck.close();
        }
    }

    /**
     * Close quietly our server, without exception handling
     */
    public void closeQuietly() {
        try {
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
