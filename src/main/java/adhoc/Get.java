package adhoc;

import crud.CRUDApi;
import interfaces.ResponseClass;
import interfaces.crud.request.GetRequest;

public class Get {
    public static void main(String[] args) {
        String id = "daa7894c-431d-431d-92ae-d9682ee863d5";
        CRUDApi crud = new CRUDApi("test");
        ResponseClass response = crud.get("name", new GetRequest(id, null));
        System.out.println("response = " + response.getMessage());
    }
}
