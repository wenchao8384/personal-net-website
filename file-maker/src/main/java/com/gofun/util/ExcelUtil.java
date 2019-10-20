package com.gofun.util;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/10/18
 */
public class ExcelUtil {
    public static void readExcel(String fileName) {
        Workbook workbook = null;
       try {
           System.out.println(fileName);
           System.out.println(ExcelUtil.class.getClassLoader().getResource(fileName).getPath());
            File xlsFile = new File(ExcelUtil.class.getClassLoader().getResource(fileName).getPath());
            // 获得工作簿对象
            workbook = Workbook.getWorkbook(xlsFile);
            // 获得所有工作表
            Sheet[] sheets = workbook.getSheets();
            // 遍历工作表
            if (sheets != null) {
                for (Sheet sheet : sheets) {
                    // 获得行数
                    int rows = sheet.getRows();
                    // 获得列数
                    int cols = sheet.getColumns();
                    // 读取数据
                    for (int row = 0; row < rows; row++) {
                        for (int col = 0; col < cols; col++) {
                            Cell cell = sheet.getCell(col, row);
                            System.out.print(cell.getContents() + " ");
                        }
                        System.out.println();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        workbook.close();
    }

    public static void main(String[] args) {
        readExcel("file/OCR.xls");
    }
}
