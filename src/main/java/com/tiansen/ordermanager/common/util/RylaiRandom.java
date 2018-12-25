package com.tiansen.ordermanager.common.util;

import java.util.Random;
import java.util.UUID;

public class RylaiRandom
{
	private static String base = "abcdefghijklmnopqrstuvwxyz0123456789";  
	private static String baseNum = "0123456789";  
	private static String baseHex="0123456789abcdef";
	/**
	 * rylai 获取纯数字字符串
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
	    Random random = new Random();  
	    StringBuffer sb = new StringBuffer();  
	    for (int i = 0; i < length; i++) {  
	        int number = random.nextInt(base.length());  
	        sb.append(base.charAt(number));  
	    }  
	    return sb.toString();  
	 } 
	/**
	 * rylai HexStr
	 * @param length
	 * @return
	 */
	public static String getRandomHex(int length) {
	    Random random = new Random();  
	    StringBuffer sb = new StringBuffer();  
	    int firstNumber = random.nextInt(8);  
        sb.append(baseHex.charAt(firstNumber)); 
	    for (int i = 1; i < length; i++) {	    	
	        int number = random.nextInt(baseHex.length());  
	        sb.append(baseHex.charAt(number));  
	    }  
	    return sb.toString();  
	 } 
	/**
	 * rylai 获取0-9 a-z字符串
	 * @param length
	 * @return
	 */
	public static String getRandomNum(int length) {
	    Random random = new Random();  
	    StringBuffer sb = new StringBuffer();  
	    for (int i = 0; i < length; i++) {  
	        int number = random.nextInt(baseNum.length());  
	        sb.append(baseNum.charAt(number));  
	    }  
	    return sb.toString();  
	 } 
	
	public static byte[] getRandomByte(int length) { //length��ʾ�����ַ����ĳ���
	    return getRandomString(length).getBytes();  
	} 
	public static String getUUid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
