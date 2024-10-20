package com.genericresponseapp.util;

import com.genericresponseapp.handler.GenericResponseHandler;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class CommonUtil {

    public static ResponseEntity<?> createBuildResponse(Object object, String message, HttpStatusCode statusCode) {
        GenericResponseHandler genericResponseHandler = new GenericResponseHandler();
        genericResponseHandler.setStatus(statusCode.value());
        genericResponseHandler.setMessage(message);
        genericResponseHandler.setData(object);
        return genericResponseHandler.create();
    }
}
