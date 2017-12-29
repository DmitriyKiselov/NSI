/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import my.guisap.entity.RowExcel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author KiselevDA
 */
public class ExelUtils {

    public ExelUtils() {
    }

    public static void SaveToExcel(RowExcel excelRow, String nameSheet, FileOutputStream output) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(nameSheet);

        CellStyle style = workbook.createCellStyle();
        style.setAlignment((short) 2);

        int rowNum = 0;
        int colNum = 0;

        Row row = sheet.createRow(rowNum++);
        Cell cell;
        for (int colnum = 0; colnum < excelRow.getColumnNames().size(); colnum++) {
            cell = row.createCell(colnum);
            cell.setCellStyle(style);
            if (excelRow.getColumnNames().get(colnum) instanceof String) {
                cell.setCellValue((String) excelRow.getColumnNames().get(colnum));
            } else if (excelRow.getColumnNames().get(colnum) instanceof Integer) {
                cell.setCellValue((Integer) excelRow.getColumnNames().get(colnum));
            }
        }

        for (List<Object> field : excelRow.getColumn()) {
            row = sheet.createRow(rowNum++);
            for (Object col : field) {
                cell = row.createCell(colNum++);
                SaveCellToExcel(col, cell, style);
            }
            colNum = 0;
        }

        for (int colnum = 0; colnum <= sheet.getLastRowNum(); colnum++) {
            sheet.autoSizeColumn(colnum);
        }

        workbook.write(output);
    }

    public static void SaveCellToExcel(Object value, Cell cell, CellStyle style) {
        boolean check;
        if (style != null) {
            cell.setCellStyle(style);
        }
        if (value == null) {
            return;
        }
        try {
            cell.setCellValue(Integer.valueOf((String) value));
            check = true;
        } catch (NumberFormatException ex) {
            check = false;
        }
        if (!check) {
            try {
                cell.setCellValue(Double.valueOf((String) value));
                check = true;
            } catch (NumberFormatException ex) {
                check = false;
            }
        }
        if (!check) {
            cell.setCellValue((String) value);
        }
    }

    public static RowExcel ReadRowFromExcel(File file) throws IOException {
        Workbook myExcelBook;
        String nameFile = file.getName();
        RowExcel resultRow = new RowExcel();

        try (FileInputStream inStream = new FileInputStream(file)) {
            if (nameFile.charAt(nameFile.length() - 1) == 'x') {
                myExcelBook = new XSSFWorkbook(inStream);
            } else {
                myExcelBook = new HSSFWorkbook(inStream);
            }
            Sheet myExcelSheet = myExcelBook.getSheet(myExcelBook.getSheetName(0));

            List<Object> list;

            DataFormatter formatter = new DataFormatter();
            for (Iterator iterator = myExcelSheet.rowIterator(); iterator.hasNext();) {
                Row row = (Row) iterator.next();
                list = new ArrayList<>();
                for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                    Cell cell = row.getCell(i);
                    list.add(formatter.formatCellValue(cell));
                }
                resultRow.getColumn().add(list);
            }
        }
        return resultRow;
    }

    public static void DownloadToExcel(String nameTable, String[][] nameFields) {
        
    }

}
