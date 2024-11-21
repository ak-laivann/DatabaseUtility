package adhoc;

import crud.CRUDApi;
import interfaces.ResponseClass;
import interfaces.crud.request.DeleteRequest;

import java.util.ArrayList;

public class Delete {
    public static void main(String[] args) {
        CRUDApi crud = new CRUDApi("test");
        ResponseClass response = crud.delete("name", new DeleteRequest<>("Chumma", new ArrayList()));
        System.out.println("response.getMessage() = " + response.getMessage());
    }
}
