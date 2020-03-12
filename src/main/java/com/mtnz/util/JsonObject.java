package com.mtnz.util;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonObject {

    private volatile static JsonObject instance;
    private JsonObject(){
        System.out.println("Singleton has loaded");
    }
    public static String getInstance(PageData pageData) throws Exception {
        if(instance==null){
            synchronized (JsonObject.class){
                if(instance==null){
                    instance=new JsonObject();
                }
            }
        }
        ObjectMapper mapper=new ObjectMapper();
        String str=mapper.writeValueAsString(pageData);
        return str;
    }
}
