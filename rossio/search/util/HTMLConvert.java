package com.rossio.search.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTML.Tag;

public class HTMLConvert {

	public static String html2Text(String inputStr){
		if (inputStr == null || inputStr.trim().length() == 0) {
			return "";
		}
		
		String html = inputStr.trim();
		String text = "";
		
		Pattern p_script, p_style, p_html;
		Matcher m_script, m_style, m_html;
		
		String reg_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
		String reg_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
		String reg_html = "<\\s*/?\\s*([a-z]|\\?xml)+[^>[\u4E00-\u9FFF]]*>";
		
		p_script = Pattern.compile(reg_script, Pattern.CASE_INSENSITIVE);
		p_style = Pattern.compile(reg_style, Pattern.CASE_INSENSITIVE);
		p_html = Pattern.compile(reg_html, Pattern.CASE_INSENSITIVE);
		
		m_script = p_script.matcher(html);
		
		html = m_script.replaceAll("");
		
		m_style = p_style.matcher(inputStr);
		html = m_style.replaceAll("");
		
		m_html = p_html.matcher(inputStr);
		html = m_html.replaceAll("");
		
		text = html;
		
		return text;
	}
	
	public static void main(String[] args) {
		String text = "<SPAN style=\"FONT-SIZE: 9pt; FONT-FAMILY: 宋体\">按国际财务报告准则审计口径，截至<SPAN lang=EN-US>2008</ span>";
		System.out.println(html2Text(text));
	}
	
}
