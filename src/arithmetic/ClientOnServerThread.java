package arithmetic;

import java.io.*;
import java.net.Socket;

/**
 * @author Dominik Ciborowski
 */
public class ClientOnServerThread implements Runnable, Closeable {

    private Socket sck;

    private BufferedReader input;

    private PrintWriter output;

    public ClientOnServerThread(Socket sck) throws IOException {
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
            handleConnection();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            closeQuietly();
        }

    }

    private void handleConnection() throws IOException {

        String fromClient = null;

        while ((fromClient = input.readLine()) != null && fromClient != Protocol.EXIT.name()) {
            System.out.println("Got: " + fromClient);

            String[] split = fromClient.split(Protocol.COMMAND_DELIM);
            Protocol cmd = null;

            try {
                cmd = Protocol.valueOf(split[0]);

                // checking if command exists in protocol enum
            } catch (IllegalArgumentException e) {
                output.println(Protocol.constructMessage(Protocol.ERR_WRONG_CMD, split[0]));
                continue;
            }

            Double firstNum = Double.parseDouble(split[1]);
            Double secondNum = null;

            switch (cmd) {
                case ADD:
                    secondNum = Double.parseDouble(split[2]);

                    output.println(Protocol.constructMessage(Protocol.RES, cmd, (firstNum + secondNum)));
                    break;

                case SUBSTR:
                    secondNum = Double.parseDouble(split[2]);

                    output.println(Protocol.constructMessage(Protocol.RES, cmd, (firstNum - secondNum)));
                    break;

                case MULT:
                    secondNum = Double.parseDouble(split[2]);

                    output.println(Protocol.constructMessage(Protocol.RES, cmd, (firstNum * secondNum)));
                    break;

                case DIV:
                    secondNum = Double.parseDouble(split[2]);

                    output.println(Protocol.constructMessage(Protocol.RES, cmd, (firstNum / secondNum)));
                    break;

                case SQRT:
                    output.println(Protocol.constructMessage(Protocol.RES, cmd, Math.sqrt(firstNum)));
                    break;

                default:
                    output.println(Protocol.constructMessage(Protocol.ERR_WRONG_CMD, cmd));
            }
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
