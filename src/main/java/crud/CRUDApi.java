package crud;

import interfaces.Method;
import interfaces.ResponseClass;
import interfaces.crud.request.DeleteRequest;
import interfaces.crud.request.GetRequest;
import interfaces.crud.request.PostRequest;
import interfaces.crud.request.PutRequest;
import interfaces.crud.response.GetResponse;
import utils.FileReader;
import utils.FileWriter;
import utils.PathBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class CRUDApi {
    private final String authKey;

    public CRUDApi(String authKey) {
        this.authKey = authKey;
    }

    public GetResponse get(String entityName, GetRequest getRequest) {

        HashMap<String, Object> message = APIHelper.helperForFileReading(entityName, getRequest.getId(), authKey);

        return new GetResponse<>(200, message.toString(), message.get("data"));
    }

    public ResponseClass post(String entityName, PostRequest<ArrayList<Object>> postRequest) {
        String filePath = PathBuilder.createPath(authKey, entityName, Method.POST);
        ArrayList<Object> readResponse = FileReader.readJsonArrayFromFile(PathBuilder.createPath(authKey, entityName, Method.POST));

        ArrayList dataToBePosted = readResponse;

        try {
            dataToBePosted = APIHelper.helperForFileWriting(readResponse, postRequest.getData(), postRequest);
        } catch (IllegalAccessException e) {
            e.getStackTrace();
        }

        return FileWriter.writeIntoFile(filePath, dataToBePosted, Method.POST);
    }

    public ResponseClass put(String entityName, PutRequest<ArrayList> putRequest) {
        String filePath = PathBuilder.createPath(authKey, entityName, Method.PUT);
        ArrayList readResponse = FileReader.readJsonArrayFromFile(PathBuilder.createPath(authKey, entityName, Method.PUT));

        ArrayList dataToBePosted = readResponse;

        try {
            dataToBePosted = APIHelper.helperForFileWriting(readResponse, putRequest.getData(), putRequest);
        } catch (IllegalAccessException e) {
            e.getStackTrace();
        }

        return FileWriter.writeIntoFile(filePath, dataToBePosted, Method.PUT);
    }

    public ResponseClass delete(String entityName, DeleteRequest<ArrayList> deleteRequest) {
        String filePath = PathBuilder.createPath(authKey, entityName, Method.DELETE);

        ArrayList readResponse = FileReader.readJsonArrayFromFile(PathBuilder.createPath(authKey, entityName, Method.DELETE));

        ArrayList dataToBePosted = readResponse;

        try {
            dataToBePosted = APIHelper.helperForFileWriting(readResponse, deleteRequest.getData(), deleteRequest);
        } catch (IllegalAccessException e) {
            e.getStackTrace();
        }

        return FileWriter.writeIntoFile(filePath, dataToBePosted, Method.DELETE);
    }
}
