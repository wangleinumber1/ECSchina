package com.jiajie.jiajieproject.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLEncodUtil {

	public static String getEncodeStr(String params){
		if(!StringUtil.checkStr(params))
			return null;
		try {
			return URLEncoder.encode(params,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getDecodeStr(String params){
		if(!StringUtil.checkStr(params))
			return null;
		try {
			return URLDecoder.decode(params,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
