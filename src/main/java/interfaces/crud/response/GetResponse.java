package interfaces.crud.response;

import interfaces.ResponseClass;

public class GetResponse<T> extends ResponseClass<T> {

    public GetResponse(int statusCode, String message, T data) {
        super(statusCode, message, data);
    }

}

