package com.mandiri.constant;

public class ResponseMessage {
    public static final String RESOURCE_NOT_FOUND = "%s with Id=%s 's not found.";
    public static String getResourceNotFound(String className, String id){
        return String.format(RESOURCE_NOT_FOUND, className, id);
    }
}
