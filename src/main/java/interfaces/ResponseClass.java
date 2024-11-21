package interfaces;

interface Responses<T> {
    int getStatusCode();

    String getMessage();

    T getData();
}

public class ResponseClass<T> implements Responses<T> {

    private final int statusCode;
    private final String message;
    private final T data;

    public ResponseClass(int statusCode, String message, T data) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public T getData() {
        return data;
    }
}
