package com.tiansen.ordermanager.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class RylaiRandom
{
	private static String base = "abcdefghijklmnopqrstuvwxyz0123456789";  
	private static String baseNum = "0123456789";  
	private static String baseHex="0123456789abcdef";
	/**
	 * tiansen 获取纯数字字符串
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
	 * tiansen HexStr
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
	 * tiansen 获取0-9 a-z字符串
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

	public static String genOrderNo() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar calendar = Calendar.getInstance();
		String dateName = df.format(calendar.getTime());

		Random ne=new Random();//实例化一个random的对象ne
		int x = ne.nextInt(999-100+1)+100;//为变量赋随机值100-999
		String random_order = String.valueOf(x);
		String order_id = dateName+random_order;
		return order_id;
	}

//	public static void main(String[] args) {
//		System.out.println("order no: " + genOrderNo());
//	}
}
