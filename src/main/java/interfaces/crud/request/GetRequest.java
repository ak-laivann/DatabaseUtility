package interfaces.crud.request;

import interfaces.Method;
import interfaces.RequestClass;

public class GetRequest<T> extends RequestClass<T> {
    public GetRequest(String id, T data) {
        super(Method.GET, data, id);
    }
}
