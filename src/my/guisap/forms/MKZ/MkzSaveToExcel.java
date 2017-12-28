/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms.MKZ;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import my.guisap.GuiStaticVariables;
import my.guisap.entity.RowExcel;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.ExelUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 *
 * @author KiselevDA
 */
public class MkzSaveToExcel {

    SqlOperations sql = new SqlOperations();
    String ID_RECORD;
    HSSFWorkbook workbook;
    List<RowExcel> allRow = new ArrayList<>();
    HSSFSheet sheet;
    int rowNum = 0;

    MkzSaveToExcel(FileOutputStream outputStream, String chooseModel) {

        DefaultTableModel mainTable = new DefaultTableModel();
        DefaultTableModel stepOneTable = new DefaultTableModel();
        DefaultTableModel articleTable = new DefaultTableModel();

        RowExcel mainRow;
        RowExcel stepOneRow;

        //получение информации для сохранения (использовалась функция tableFill, для того что бы не писать новую)
        sql.tableFill("select MODEL, ID , YEAR,GROUPMANWOMAN,METHOD_CONSTR,SHIN_VIEW,SEASON,TYPE_OF_PRODUCT,STYLE_MODEL,MAT_LINING,MAT_SOLE,FASON_LAST,CODE_LAST,DESIGNER"
                + " from GUI_SAP.MKZ_LIST_HEAD where model = '" + chooseModel + "'", mainTable);
        ID_RECORD = mainTable.getValueAt(0, 1).toString();

        mainRow = new RowExcel(mainTable, "Main", ID_RECORD);

        sql.tableFill("select * from GUI_SAP.MKZ_STEP2 where id = '" + ID_RECORD + "'", stepOneTable);

        stepOneRow = new RowExcel(stepOneTable, "StepOne", ID_RECORD);

        sql.tableFill("select lpad(ART_ID,2,'0'),ART,STATUS from GUI_SAP.MKZ_MODEL_ART where model_id = '" + ID_RECORD + "' order by ART_ID", articleTable);

        for (int i = 0; i < articleTable.getRowCount(); i++) {
            for (String[] name : GuiStaticVariables.listAllBlocksSketch) {
                allRow.add(new RowExcel(Load_Elem(name[0], articleTable.getValueAt(i, 1).toString()), name[2], articleTable.getValueAt(i, 1).toString()));
            }
        }

        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("MKZ");

        CellStyle style = workbook.createCellStyle();
        Short x = 2;
        style.setAlignment(x);

//        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        paintRowToExel(mainRow, 0, style, false, 0, false);
        paintRowToExel(stepOneRow, 0, style, false, 0, false);
        paintRowToExel(null, 0, style, false, 0, false);

        for (RowExcel data : allRow) {
            paintRowToExel(data, 2, style, true, 25, true);
        }

        autoSizeColumns();

        try {
            workbook.write(outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DefaultTableModel Load_Elem(String Table, String art) {
        DefaultTableModel tempData = new DefaultTableModel();
        String quer = "select * from GUI_SAP." + Table + " \n"
                + "where art in (select ART from GUI_SAP.MKZ_MODEL_ART where model_id = '" + ID_RECORD + "' and ART= '" + art + "') \n"
                + "order by ART,ID";
        sql.tableFill(quer, tempData);
        return tempData;
    }

    private void autoSizeColumns() {
        int sheetCount = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            HSSFSheet sheet = workbook.getSheetAt(i);
            for (int colnum = 0; colnum <= sheet.getLastRowNum(); colnum++) {
                sheet.autoSizeColumn(colnum);
            }
        }
    }

    private void paintRowToExel(RowExcel rowToPaint, int startCell, CellStyle style, boolean needMargin, int margin, boolean needHeader) {

        int colNum = startCell;
        Row row = sheet.createRow(rowNum++);
        Cell cell;

        if (rowToPaint != null) {
            if (rowToPaint.getNameRow().equals(GuiStaticVariables.listAllBlocksSketch.get(0)[2])) {
                cell = row.createCell(0);
                cell.setCellValue(rowToPaint.getId());
                row = sheet.createRow(rowNum++);
            }

            if (needHeader) {
                cell = row.createCell(0);
                cell.setCellValue(rowToPaint.getNameRow());
            }

            for (Object field : rowToPaint.getColumnNames()) {
                cell = row.createCell(colNum++);
                ExelUtils.SaveCellToExcel(field, cell, style);
                if (colNum - startCell == rowToPaint.getColumnNames().size() - 3 && needMargin) {
                    colNum = margin + startCell;
                }
            }

            colNum = startCell;

            for (List<Object> field : rowToPaint.getColumn()) {
                row = sheet.createRow(rowNum++);
                for (Object col : field) {
                    cell = row.createCell(colNum++);
                    ExelUtils.SaveCellToExcel(col, cell, style);
                    if (colNum - startCell == field.size() - 3 && needMargin) {
                        colNum = margin + startCell;
                    }
                }
                colNum = startCell;
            }
        }
    }
}
