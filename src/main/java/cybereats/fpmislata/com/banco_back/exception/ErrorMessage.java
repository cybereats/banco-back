package cybereats.fpmislata.com.banco_back.exception;

public class ErrorMessage {

    private String error;
    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(Exception exception) {
        this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
    }

    public ErrorMessage(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
