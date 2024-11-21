package interfaces.crud.request;

import interfaces.Method;
import interfaces.RequestClass;

public class DeleteRequest<T> extends RequestClass<T> {
    public DeleteRequest(String id, T data) {
        super(Method.DELETE, data, id);
    }
}


