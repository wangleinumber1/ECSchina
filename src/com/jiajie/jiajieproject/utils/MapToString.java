package com.jiajie.jiajieproject.utils;

import java.util.Map;

public class MapToString {

	public static String getQueryParams(Map<String, Object> maps) {
		if(null == maps) return null;
		StringBuilder sbuild = new StringBuilder();
		for(Map.Entry<String, Object> map:maps.entrySet()){
			String key = map.getKey();
			Object value = map.getValue();
			sbuild.append(key);
			sbuild.append("=");
			if(value instanceof String){
				sbuild.append(ParamsEncodeUtil.encodeParam((String)value));
			}else{
				sbuild.append(value);
			}
			//sbuild.append(value);
			sbuild.append("&");
		}
		sbuild.deleteCharAt(sbuild.length() - 1);
		return sbuild.toString();
	}
}
