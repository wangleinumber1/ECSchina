package com.jiajie.jiajieproject.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlToString {
	public static String HtmlToTextGb2312(String inputString) 
	    { 
	              String htmlStr = inputString; //含html标签的字符串 
	              String textStr =""; 
	              Pattern p_script; 
	              Matcher m_script; 
	              Pattern p_style; 
	              Matcher m_style; 
	              Pattern p_html; 
	              Matcher m_html;
	              Pattern p_houhtml; 
	              Matcher m_houhtml;
	              Pattern p_spe; 
	              Matcher m_spe;
	              Pattern p_blank; 
	              Matcher m_blank;
	              Pattern p_table; 
	              Matcher m_table;
	              Pattern p_enter; 
	              Matcher m_enter;
	           
	              try { 
	               String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; 
	               //定义script的正则表达式.
	               String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; 
	               //定义style的正则表达式. 
	               String regEx_html = "<[^>]+>"; 
	               //定义HTML标签的正则表达式 
	               String regEx_houhtml = "/[^>]+>"; 
	               //定义HTML标签的正则表达式 
	               String regEx_spe="\\&[^;]+;";
	               //定义特殊符号的正则表达式
	               String regEx_blank=" +";
	               //定义多个空格的正则表达式
	               String regEx_table="\t+";
	               //定义多个制表符的正则表达式
	               String regEx_enter="\n+";
	               //定义多个回车的正则表达式
	
	               p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
	               m_script = p_script.matcher(htmlStr); 
	               htmlStr = m_script.replaceAll(""); //过滤script标签
	
	               p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
	               m_style = p_style.matcher(htmlStr); 
	               htmlStr = m_style.replaceAll(""); //过滤style标签 
	              
	               p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
	               m_html = p_html.matcher(htmlStr); 
	               htmlStr = m_html.replaceAll(""); //过滤html标签 
	               
	               p_houhtml = Pattern.compile(regEx_houhtml,Pattern.CASE_INSENSITIVE); 
	               m_houhtml = p_houhtml.matcher(htmlStr); 
	               htmlStr = m_houhtml.replaceAll(""); //过滤html标签 
	               
	               p_spe = Pattern.compile(regEx_spe,Pattern.CASE_INSENSITIVE); 
	               m_spe = p_spe.matcher(htmlStr); 
	               htmlStr = m_spe.replaceAll(""); //过滤特殊符号 
	               
	               p_blank = Pattern.compile(regEx_blank,Pattern.CASE_INSENSITIVE); 
	               m_blank = p_blank.matcher(htmlStr); 
	               htmlStr = m_blank.replaceAll(" "); //过滤过多的空格
	               
	               p_table = Pattern.compile(regEx_table,Pattern.CASE_INSENSITIVE); 
	               m_table = p_table.matcher(htmlStr); 
	               htmlStr = m_table.replaceAll(" "); //过滤过多的制表符
	
	p_enter = Pattern.compile(regEx_enter,Pattern.CASE_INSENSITIVE); 
	               m_enter = p_enter.matcher(htmlStr); 
	               htmlStr = m_enter.replaceAll(" "); //过滤过多的制表符
	               
	               textStr = htmlStr; 
	              
	              }catch(Exception e) 
	              { 
	                    System.err.println("Html2Text: " + e.getMessage()); 
	              } 
	           
	              return textStr;//返回文本字符串 
	    }
}

