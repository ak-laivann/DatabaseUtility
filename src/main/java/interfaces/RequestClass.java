package interfaces;

interface Request<T> {

    Method getMethod();

    T getData();

    String getId();
}

public class RequestClass<T> implements Request<T> {

    private final Method method;
    private final T data;

    private final String id;

    public RequestClass(Method method, T data, String id) {
        this.id = id;
        this.method = method;
        this.data = data;
    }

    @Override
    public String getId() {
        return id;
    }


    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public T getData() {
        return data;
    }
}
