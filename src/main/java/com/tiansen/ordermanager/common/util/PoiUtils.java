package com.tiansen.ordermanager.common.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class PoiUtils {
    private final static String excel2003L =".xls";    //2003- 版本的excel
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel
    private final static DecimalFormat df = new DecimalFormat("#");
    public static void main(String[] args){
        File file = new File("D:\\InitiModel.xlsx");
        try {
            InputStream is = new FileInputStream(file);
            try {
                Map<String, List<List<Object>>> sheetsMapList = getSheetsMapList(is, "InitiModel.xlsx", 0);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 描述：获取IO流中的数据，组装成Map<String,List<List<Object>>> 对象
     * @param in,fileName
     * @return
     * @throws IOException
     */
    public static Map<String,List<List<Object>>> getSheetsMapList(InputStream in,String fileName,int ...sheetIndexs) throws Exception{
        Map<String,List<List<Object>>> sheetMap=new HashMap<>();
        //创建Excel工作薄
        Workbook work=null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(excel2003L.equalsIgnoreCase(fileType)){
            work = new HSSFWorkbook(in);  //2003-
        }else if(excel2007U.equalsIgnoreCase(fileType)){
            work = new XSSFWorkbook(in);  //2007+
        }else{
            throw new Exception("解析的文件格式有误！");
        }

        Sheet sheet = null;
        Row row = null;
        Cell cell = null;


        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            try {

                int currentIndex=sheetIndexs[i];

                sheet = work.getSheetAt(currentIndex);
                if(sheet==null){continue;}

                List<List<Object>> list = new ArrayList<List<Object>>();
                int rowLen=sheet.getLastRowNum();
                int cellLen=sheet.getRow(0).getLastCellNum();
                for (int j = 0; j <=rowLen; j++) {
                    row = sheet.getRow(j);
                    if(row==null){continue;}
                    //遍历所有的列
                    List<Object> li = new ArrayList<Object>();
                    String value=" ";
                    for (int y = 0; y < cellLen; y++) {
                        cell = row.getCell(y);
                        if(null!=cell){
                            value=getCellValue(cell).toString();
                        }else{
                            value=" ";
                        }
                        li.add(value);
                    }
                    list.add(li);
                }
                sheetMap.put("sheet"+currentIndex,list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        work.close();
        return sheetMap;
    }
    public static  String getCellValue(Cell cell){
        String value = "";
        switch (cell.getCellTypeEnum()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                short format = cell.getCellStyle().getDataFormat();
                SimpleDateFormat sdf = null;
                if(format == 14 || format == 31 || format == 57 || format == 58){
                    //日期
                    sdf = new SimpleDateFormat("yyyy/MM/dd");
                    value = formatDateValue(cell, sdf);
                }else if (format == 20 || format == 32) {
                    //时间
                    sdf = new SimpleDateFormat("HH:mm");
                    value = formatDateValue(cell, sdf);
                } else {
                    value = df.format(cell.getNumericCellValue());
                }
                break;
            default:
                break;
        }
        return value;
    }

    private static String formatDateValue(Cell cell, SimpleDateFormat sdf) {
        double cellValue = cell.getNumericCellValue();
        Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cellValue);
        String value = sdf.format(date);
        return value;
    }
}