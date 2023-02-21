package helpers;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;
import org.testng.Reporter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class EditDtoHelper {

    public void changeFieldValue(Object object, String field, Object value) {
        Field rootObjectField = null;
        Object rootObject = object;
        Class<?> rootClassType = object.getClass();

        if (field.contains(".")) {
            String[] fieldPath = field.split("\\.");
            StringBuilder processedFields = new StringBuilder();

            for (String objectFieldName : fieldPath) {
                processedFields.append(objectFieldName).append(".");
                Field innerObjectField = ReflectionUtils.findField(rootClassType, objectFieldName);

                Object innerObject = new Object();

                try {
                    ReflectionUtils.makeAccessible(innerObjectField);
                    innerObject = innerObjectField.get(rootObject);
                } catch (IllegalAccessException e) {
                    System.out.println("Can not set value for object: " + e);
                }

                Class<?> innerClassType = innerObjectField.getType();
                boolean isCustomClassType = !innerClassType.getName().contains("java.lang");

                if (isCustomClassType) {
                    if (innerObject == null) {
                        try {
                            innerObject = innerClassType.newInstance();
                            innerObjectField.set(rootObject, innerObject);
                        } catch (InstantiationException | IllegalAccessException e) {
                            System.out.println("Can not create a new instance of " + innerClassType.getSimpleName());
                        }
                    }

                    rootObject = innerObject;
                }

                rootClassType = innerObjectField.getType();
                rootObjectField = innerObjectField;
            }

            processedFields.setLength(0);
        } else {
            rootObjectField = ReflectionUtils.findField(rootClassType, field);
        }

        if (rootObjectField != null && rootObject != null) {
            ReflectionUtils.makeAccessible(rootObjectField);
            ReflectionUtils.setField(rootObjectField, rootObject, value);
        }
    }

    public String removeNullField(Object object, String field) {
        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            Reporter.log(String.format("An error occured while converting DTO %s to string %n %s", object, e));
        }

        int startIndex;

        if (field.contains(".")) {
            long numberOfObjects = field.chars().filter(ch -> ch == '.').count();
            String objectField = "";

            for (int i = 0; i < numberOfObjects; i++) {
                objectField = field.substring(0, field.indexOf('.'));
                field = field.substring(field.indexOf('.') + 1);
            }

            startIndex = json.indexOf(field, json.indexOf(objectField)) - 1;
        } else {
            startIndex = json.indexOf(field) - 1;
        }

        // Magic number 7 is for ': null'
        int endIndex = startIndex + field.length() + 7;

        // Removing extra comma if any
        if (json.charAt(endIndex) == ',') {
            endIndex += 1;
        } else {
            // Remove extra comma before the field if any
            if (json.charAt(startIndex - 1) == ',' && json.charAt(endIndex) != ',') {
                startIndex -= 1;
            }
        }

        return json.substring(0, startIndex) + json.substring(endIndex);
    }
}