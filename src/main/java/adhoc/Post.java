package adhoc;

import crud.CRUDApi;
import interfaces.ResponseClass;
import interfaces.crud.request.PostRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Post {
    public static void main(String[] args) {
        CRUDApi crud = new CRUDApi("test");
        Map<String, String> map = Map.of("key3", "value3");
        Map<String, String> value = new HashMap<String, String>(map);
        value.put("key4", "value4");
        ArrayList<Object> valueAsArrayList = new ArrayList<>();
        valueAsArrayList.addFirst(value);
        ResponseClass response = crud.post("name", new PostRequest<ArrayList<Object>>(valueAsArrayList));
        System.out.println("response = " + response);
    }
}
