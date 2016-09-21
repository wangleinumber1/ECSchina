package com.jiajie.jiajieproject.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

public class JsonUtil {

	/** 
     * 将json对象转换成Map 
     * @param jsonObject json对象 
     * @return Map对象 
     */ 
    public static Map<String, Object> jsonObjtoMap(JSONObject jsonObject){ 
    	if(null == jsonObject) return null;
        Map<String, Object> result = new HashMap<String, Object>(); 
        Iterator<String> iterator = jsonObject.keys(); 
        while (iterator.hasNext()) { 
            String key = iterator.next(); 
            Object value = jsonObject.opt(key);
            result.put(key, value); 
        } 
        return result; 
    } 
}
