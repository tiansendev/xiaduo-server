package com.tiansen.ordermanager.common.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

public class HexAscByteUtil {
	private static String hexString = "0123456789ABCDEF";
	public static void main(String[] args){
		long lg=655350;
		String hexLong= HexAscByteUtil.long2Hex(lg,4);
		System.out.println("hexLong3byte:"+hexLong);
		System.out.println("hexLong:"+ HexAscByteUtil.long2Hex(lg));
		System.out.println("long:"+ HexAscByteUtil.hex2Long(hexLong));
	}
	public static String upsetString(String src){
		char[] chars=src.toCharArray();
		int charLen=chars.length;
		char[] newChars=new char[charLen];
		boolean[] mark = new boolean[charLen];
		for(int i = 0 ; i < charLen ; i ++) {
			//a.创建随机数
			Random rd = new Random();
			//b.获取随机数的下标
			int index = rd.nextInt(charLen);
			//c.判断标识
			if(mark[index] == false) {
				newChars[i] = chars[index];
				mark[index]=true;
			}else {
				System.out.println("===");
				i --; //该次取随机数取到的是洗过的牌，则重新再取一次
			}
		}
		return new String(newChars);
	}
	public static String asc2hex(String asc) {
		byte[] bytes = asc.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString().toUpperCase();
	}

	public static String hex2asc(String hex){
		hex = hex.toUpperCase();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(hex.length() / 2);
		for (int i = 0; i < hex.length(); i += 2)
			baos.write((hexString.indexOf(hex.charAt(i)) << 4 | hexString
					.indexOf(hex.charAt(i + 1))));

		return new String(baos.toByteArray()).toUpperCase();
	}
	public static String hex2asc(String hex,String charsetName) {
		hex = hex.toUpperCase();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(hex.length() / 2);
		for (int i = 0; i < hex.length(); i += 2)
			baos.write((hexString.indexOf(hex.charAt(i)) << 4 | hexString
					.indexOf(hex.charAt(i + 1))));
		String strAscRtn="";
		try {
			strAscRtn=new String(baos.toByteArray(), charsetName).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strAscRtn;
	}
	public static String str2HexStr(String str)
	{

		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;

		for (int i = 0; i < bs.length; i++)
		{
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
			sb.append(' ');
		}
		return sb.toString().trim();
	}
	public static String hex2BinByOne(String hexString) {
		String strZero = "";
		String strBin = "";
		int intTempBinLen = 0;
		BigInteger decVal = new BigInteger(hexString, 16);
		strBin = decVal.toString(2);
		if (strBin.length() % 4 != 0) {
			intTempBinLen = 4 * hexString.length() - strBin.length();
			for (int j = 0; j < intTempBinLen; j++) {
				strZero = strZero + "0";
			}
			strBin = strZero + strBin;
		}
		return strBin;
	}
	public static String bin2Hex(String binString) {
		String strHex = "";
		String strTemp = "";
		int intLen = binString.length() / 4;
		int intVal;
		for (int i = 0; i < intLen; i++) {
			strTemp = binString.substring(i * 4, (i + 1) * 4);
			intVal = Integer.valueOf(strTemp, 2).intValue();
			strHex = strHex + Integer.toHexString(intVal);

		}

		return strHex;
	}

	public static String byteArr2HexStr(byte[] arrB){
		if(arrB==null){
			return "";
		}
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}
	public static String byteToHexString(byte src){
		String hv = Integer.toHexString(src & 0xFF);
		if (hv.length() < 2) {
			hv = "0"+hv;
        }
		return hv.toUpperCase();
	}
	public static byte HexStringToByte(String src){
		return (byte) Integer.parseInt(src, 16);		
	}
	public static int byteToInt(byte src){
		return src & 0xFF;
	}

	public static byte[] hexStr2ByteArr(String hex){		
		byte[] arrB = hex.getBytes();
		int iLen = arrB.length;

		// ???????????????????????????�A????????????????2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	public static String getRandomString(int length,boolean isHex) { //length?????????????????
	    String base = "0123456789ABCDEF";
	    if(!isHex)
	    	base="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";   //???????????????????";
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }
	public static String twoCharToHex(String asc){
		if (asc == null || asc.equals("")) {
			return null;
		}
		char[] hexChars = asc.toCharArray();
		int length = asc.length();
		StringBuffer sb = new StringBuffer();
		int intTemp = 0;
		for (int i = 0; i < length; i++) {
			if (hexChars[i] < 65) {
				intTemp = hexChars[i] % 16;
			} else if (hexChars[i] >= 65 && hexChars[i] < 97) {
				intTemp = (hexChars[i] - 55) % 16;
			} else if (hexChars[i] >= 97 && hexChars[i] <= 127) {
				intTemp = (hexChars[i] - 87) % 16;
			}
			sb.append(Integer.toHexString(intTemp));
		}
		return sb.toString().toUpperCase();
	}
	public static int hex2Int(String strHexVal){
		strHexVal=strHexVal.toUpperCase();
		return Integer.valueOf(strHexVal,16).intValue();
		
	}

	public static String int2Hex(int intVal,int hexByteLen){
		String strHex= Integer.toHexString(intVal).toUpperCase();
		while (strHex.length()<hexByteLen*2){
			strHex="0"+strHex;
		}
		return strHex;
	}
	public static String int2Hex(int intVal){
		String strHex= Integer.toHexString(intVal).toUpperCase();
		if(strHex.length()%2!=0)
			return "0"+strHex;
		else
			return strHex;
	}
	public static long hex2Long(String strHexVal){
		strHexVal=strHexVal.toUpperCase();
		return Long.valueOf(strHexVal,16).longValue();

	}
	public static String long2Hex(long longVal,int hexByteLen){
		String strHex= Long.toHexString(longVal).toUpperCase();
		while (strHex.length()<hexByteLen*2){
			strHex="0"+strHex;
		}
		return strHex;
	}
	public static String long2Hex(long longVal){
		String strHex= Long.toHexString(longVal);
		if(strHex.length()%2!=0){
			strHex="0"+strHex;
		}
		return strHex;
	}
	public static String addChar(String strSource, int intLen, boolean isFront,
			String strChar) {
		int intSourceLen = strSource.getBytes().length;
		if (isFront) {
			for (int i = intSourceLen; i < intLen; i++) {
				strSource = strChar + strSource;
			}
		} else {
			for (int i = intSourceLen; i < intLen; i++) {
				strSource = strSource + strChar;
			}
		}
		return strSource;
	}
	public static byte getByteFromInt(int intInput,int intIndex){
		int intMv=24-(intIndex)*8;
		byte byt=(byte)((intInput>>intMv)&0xFF);
		return byt;
	}
	public static int getIntFromByte(byte[] byt){
		int intBytLen=byt.length;
		int intValue=0;
		for(int i=0;i<intBytLen && i<4;i++){
			intValue+=byt[i]*Math.pow(16,intBytLen-i);
		}
		return intValue;
	}
	public static byte getBCC(byte[] bytData){
    	int intLen=bytData.length;
    	byte bytBcc=0x00;
    	for(int i=0;i<intLen;i++){
    		bytBcc^=bytData[i];
    	}
    	return bytBcc;
    }
	public static byte getBCC(byte[] bytData,int intStart,int intEnd){
		intEnd=intEnd>bytData.length-1? bytData.length-1:intEnd;
    	byte bytBcc=0x00;
    	for(int i=intStart;i<=intEnd;i++){
    		bytBcc^=bytData[i];
    	}
    	return bytBcc;
    }
	/** 
     * �ж϶�������������ÿһ�������Ƿ�Ϊ��: ����Ϊnull���ַ����г���Ϊ0�������ࡢMapΪempty 
     *  
     * @param obj 
     * @return 
     */  
    public static boolean isNullOrEmpty(Object obj) {  
        if (obj == null)  
            return true;  
  
        if (obj instanceof CharSequence)  
            return ((CharSequence) obj).length() == 0;  
  
        if (obj instanceof Collection)  
            return ((Collection<?>) obj).isEmpty();  
  
        if (obj instanceof Map)  
            return ((Map<?, ?>) obj).isEmpty();  
  
        if (obj instanceof Object[]) {  
            Object[] object = (Object[]) obj;  
            if (object.length == 0) {  
                return true;  
            }  
            boolean empty = true;  
            for (int i = 0; i < object.length; i++) {  
                if (!isNullOrEmpty(object[i])) {  
                    empty = false;  
                    break;  
                }  
            }  
            return empty;  
        }  
        return false;  
    }  
}
