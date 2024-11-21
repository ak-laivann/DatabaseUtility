package adhoc;

import crud.CRUDApi;
import interfaces.ResponseClass;
import interfaces.crud.request.PutRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Put {
    public static void main(String[] args) {
        CRUDApi crud = new CRUDApi("test");
        Map<String, String> value = new HashMap<>(Map.of("key6", "value6"));
        ArrayList<Object> valueAsArrayList = new ArrayList<>();
        valueAsArrayList.addFirst(value);
        ResponseClass response = crud.put("name", new PutRequest<>(valueAsArrayList, "daa7894c-431d-431d-92ae-d9682ee863d5"));
        System.out.println("response.getMessage() = " + response.getMessage());
    }
}
