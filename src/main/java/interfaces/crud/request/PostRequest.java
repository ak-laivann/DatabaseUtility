package interfaces.crud.request;

import interfaces.Method;
import interfaces.RequestClass;

public class PostRequest<T> extends RequestClass<T> {
    public PostRequest(T data) {
        super(Method.POST, data, "");
    }
}
