package interfaces.crud.response;

import interfaces.ResponseClass;

public class DeleteResponse<T> extends ResponseClass<T> {
    public DeleteResponse(int statusCode, String message, T data) {
        super(statusCode, message, data);
    }
}
