package crud;

import interfaces.Method;
import interfaces.RequestClass;
import utils.FileReader;
import utils.PathBuilder;
import utils.STATUS;

import java.util.*;
import java.util.stream.Collectors;

public class APIHelper {

    /*
     * request -> data read from the file. let's name this "input"
     * based on the method
     * POST ->
     *   * request -> data to be posted -> array of single object and input.
     *   * response -> create a UUID with id as key and concat that to input.
     * PUT ->
     *   * request -> data to be put with id also included and input.
     *   * response -> return the data concatenated with input.
     * DELETE ->
     *   * request -> Array of objects with id only.
     *   * response -> return the input filtering out the object with id as id.
     */
    public static ArrayList<?> helperForFileWriting(ArrayList<Object> dataAlreadyInTheFile, ArrayList<Object> dataToBePosted, RequestClass request) throws IllegalAccessException {
        Method method = request.getMethod();

        ArrayList<?> response = dataAlreadyInTheFile;

        Map dataToWrite = Collections.emptyMap();

        if (!dataToBePosted.isEmpty()) {
            dataToWrite = (Map) dataToBePosted.getFirst();
        }

        switch (method) {
            case POST:
                response = createUUIDAndConcat(dataAlreadyInTheFile, dataToWrite);
                break;
            case PUT:
                if (!request.getId().isEmpty()) {
                    response = updateThatOneDataAndConcatWithTheArray(dataAlreadyInTheFile, request.getId(), dataToWrite);
                } else {
                    throw new NullPointerException("Id should be present in order to perform put operation");
                }
                break;
            case DELETE:
                if (!request.getId().isEmpty()) {
                    response = filterOutTheProvidedIdAndReWriteTheData(dataAlreadyInTheFile, request.getId());
                } else {
                    throw new NullPointerException("Id should be present in order to perform delete operation");
                }

                break;
            case GET:
                throw new IllegalAccessException();
            default:
                System.out.println("No such method");
        }

        return response;
    }

    private static ArrayList<Object> updateThatOneDataAndConcatWithTheArray(ArrayList<Object> data, String id, Map<String, Object> dataToBePosted) {

        ArrayList<Object> response = new ArrayList<>();

        if (!data.isEmpty()) {
            boolean isUpdated = false;

            for (Object item : data) {
                if (item instanceof Map<?, ?> currentItem) {

                    if (id.equals(currentItem.get("id"))) {
                        dataToBePosted.put("id", id);

                        response.add(dataToBePosted);
                        isUpdated = true;
                    } else {
                        response.add(item);
                    }
                } else {
                    response.add(item);
                }
            }

            if (!isUpdated) {
                throw new IllegalArgumentException("No entry with the given id found in the array.");
            }

            ArrayList responseToBeReturned = buildResponseWithIdAndDataAsSeparateKeys(response, id);
            return responseToBeReturned;
        } else {
            System.out.println("No entries in the JSON file");
            throw new UnsupportedOperationException("There is no entry in the array");
        }
    }

    private static ArrayList<?> filterOutTheProvidedIdAndReWriteTheData(ArrayList<Object> data, String id) {
        ArrayList<Object> response = new ArrayList<>();

        if (!data.isEmpty()) {
            boolean isUpdated = false;

            for (Object item : data) {
                if (item instanceof Map<?, ?> currentItem) {
                    if (!id.equals(currentItem.get("id"))) {
                        response.add(currentItem);
                        isUpdated = true;
                    }
                } else {
                    response.add(item);
                }
            }

            if (!isUpdated) {
                throw new IllegalArgumentException("No entry with the given id found in the array.");
            }

            ArrayList responseToBeReturned = buildResponseWithIdAndDataAsSeparateKeys(response, id);
            return responseToBeReturned;
        } else {
            System.out.println("No enteries in the json file");
            throw new UnsupportedOperationException("There is no entry in the array");
        }
    }

    /*
     * request ->  data read from the file. let's name this "input".
     * GET ->
     *   * request -> Array of objects, id to return.
     *   * response -> filtering that id and returning it as array of objects.
     */
    static HashMap helperForFileReading(String entityName, String id, String authKey) {
        HashMap<String, Object> message = new HashMap<String, Object>();
        ArrayList<Object> response = FileReader.readJsonArrayFromFile(PathBuilder.createPath(authKey, entityName, Method.GET));

        if (!id.isEmpty()) {
            List<Object> filteredResponse = response.stream().filter(item -> {
                if (item instanceof Map) {
                    return id.equals(((Map) item).get("id").toString());
                }
                return false;
            }).collect(Collectors.toList());

            if (!filteredResponse.isEmpty()) {
                response = new ArrayList<>(filteredResponse);
            } else {
                message.put("status", STATUS.ERROR.name());
                message.put("message", "No matching entity found with the provided ID.");
                return message;
            }
        }

        message.put("data", response);
        message.put("status", STATUS.SUCCESS.name());
        message.put("message", "Success");

        return message;
    }

    private static ArrayList createUUIDAndConcat(ArrayList dataAlreadyInTheFile, Map dataToBePosted) {
        String id = UUID.randomUUID().toString();

        dataToBePosted.put("id", id);

        dataAlreadyInTheFile.add(dataToBePosted);

        ArrayList response = buildResponseWithIdAndDataAsSeparateKeys(dataAlreadyInTheFile, id);

        return response;
    }

    private static ArrayList buildResponseWithIdAndDataAsSeparateKeys(ArrayList dataToBePosted, String id) {
        ArrayList response = new ArrayList();

        Map responseAsMap = new HashMap<>();
        responseAsMap.put("id", id);
        responseAsMap.put("data", dataToBePosted);

        response.add(responseAsMap);
        return response;
    }
}
