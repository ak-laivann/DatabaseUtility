package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.Method;
import interfaces.ResponseClass;
import interfaces.crud.response.DeleteResponse;
import interfaces.crud.response.PostResponse;
import interfaces.crud.response.PutResponse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FileWriter<T> {

    public static ResponseClass writeIntoFile(String filePath, ArrayList data, Method method) throws IllegalArgumentException {
        STATUS status = STATUS.INITIATED;

        if (isTheDataProvidedAnArrayListOrNot(data, new String[]{"id"})) {
            System.out.println("Invalid Data: Missing Mandatory fields" + Arrays.toString(new String[]{"id"}));
            return new ResponseClass<>(400, "Invalid Data: Missing Mandatory fields" + Arrays.toString(new String[]{"id"}), null);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        IdAndData result = extractIdAndDataSeparately(data);

        try {
            String jsonData = objectMapper.writeValueAsString(result.value());
            objectMapper.writeValue(new File(filePath), result.value().get(0));

            status = STATUS.SUCCESS;
        } catch (IOException e) {
            System.out.println("Error happened");
            status = STATUS.ERROR;
            e.printStackTrace();
        }

        Map<String, String> returnResponse = buildMessage(method, result.id(), status);

        return getResponseBasedOnMethod(method, returnResponse);
    }

    private static IdAndData extractIdAndDataSeparately(ArrayList data) {
        Map assda = (Map) data.get(0);
        String id = (String) assda.get("id");
        ArrayList value = new ArrayList();
        value.add(assda.get("data"));
        IdAndData result = new IdAndData(id, value);
        return result;
    }

    private static Map<String, String> buildMessage(Method method, String id, STATUS status) {
        Map<String, String> response = new HashMap();
        if (method != Method.DELETE) {
            response.put("id", id);
        }
        response.put("status", status.name());
        return response;
    }

    private static ResponseClass getResponseBasedOnMethod(Method method, Map<String, String> message) {
        ResponseClass response = new ResponseClass(200, message.toString(), null);

        switch (method) {
            case POST:
                response = new PostResponse(200, message.toString(), null);
                break;
            case PUT:
                response = new PutResponse(200, message.toString(), null);
                break;
            case DELETE:
                response = new DeleteResponse(200, message.toString(), null);
                break;
            case GET:
                throw new IllegalArgumentException();
            default:
                break;
        }
        return response;
    }

    private static boolean isTheDataProvidedAnArrayListOrNot(ArrayList<Object> data, String[] requiredFields) {
        if (data == null || data.isEmpty()) {
            return false;
        }

        for (Object item : data) {
            for (String fieldName : requiredFields) {
                if (!hasField(item, fieldName)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean hasField(Object item, String fieldName) {
        Field[] fields = item.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    private record IdAndData(String id, ArrayList value) {
    }
}
