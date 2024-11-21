package interfaces.crud.request;

import interfaces.Method;
import interfaces.RequestClass;

public class PutRequest<T> extends RequestClass<T> {
    public PutRequest(T data, String id) {
        super(Method.PUT, data, id);
    }
}
