package arithmetic;

/**
 * Enum for protocol.
 * @author Dominik Ciborowski
 */
public enum Protocol {

    ADD(2), SUBSTR(2), MULT(2), DIV(2), SQRT(1), EXIT(0),

    // result in form of: RES OP result, ex: RES ADD 2.0
    RES(2),

    //ERRORS
    ERR_WRONG_PARAMS(1), ERR_SERVER_ERROR(0), ERR_WRONG_CMD(1);

    public static final String COMMAND_DELIM = "\t";

    private int paramNumber;

    Protocol(int paramNumber) {
        this.paramNumber = paramNumber;
    }

    public int getParamNumber() {
        return paramNumber;
    }

    public static String constructMessage(Protocol command, Object... params) {
        if (command.paramNumber != params.length) {
            throw new RuntimeException("Wrong param number!");
        }

        StringBuilder stringBuilder = new StringBuilder(command.name());
        stringBuilder.append(COMMAND_DELIM);

        for (Object param : params) {
            stringBuilder.append(param);
            stringBuilder.append(COMMAND_DELIM);
        }

        return stringBuilder.toString();
    }
}
