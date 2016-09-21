package com.jiajie.jiajieproject.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/*
 * 对参数数据的编解码
 */
public class ParamsEncodeUtil {
	
	public static String encodeParam(String params){
		if(!StringUtil.checkStr(params))return null;
		try {
			return URLEncoder.encode(params, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String decodeParam(String params){
		if(!StringUtil.checkStr(params))return null;
		try {
			return URLDecoder.decode(params, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
