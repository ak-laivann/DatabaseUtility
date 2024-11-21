package interfaces.crud.response;

import interfaces.ResponseClass;

public class PutResponse<T> extends ResponseClass<T> {

    public PutResponse(int statusCode, String message, T data) {
        super(statusCode, message, data);
    }
}
