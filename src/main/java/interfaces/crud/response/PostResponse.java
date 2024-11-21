package interfaces.crud.response;

import interfaces.ResponseClass;

public class PostResponse<T> extends ResponseClass<T> {

    public PostResponse(int statusCode, String message, T data) {
        super(statusCode, message, data);
    }
}
