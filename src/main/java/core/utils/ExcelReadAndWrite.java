package core.utils;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/***
 * @author Sai Ram Prasath
 */
public class ExcelReadAndWrite {
    private static String fileName;
    private static Workbook wb;
    private Sheet ws;
    private Logger logger = Logger.getLogger(ExcelReadAndWrite.class);

    public ExcelReadAndWrite(String fileName, String sheetName) {
        if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
            if (ExcelReadAndWrite.fileName == null || !ExcelReadAndWrite.fileName.equals(fileName)) {
                try {
                    wb = WorkbookFactory.create(new FileInputStream(fileName));
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    logger.error(e.getStackTrace());
                }
            }
            ws = wb.getSheet(sheetName);
            ExcelReadAndWrite.fileName = fileName;
        }

    }

    public void removeContents() {
        try {
            for (int i = 1; i <= ws.getLastRowNum(); i++) {
                ws.removeRow(ws.getRow(i));
                ws.createRow(i);
            }
            FileOutputStream fos = new FileOutputStream(fileName);
            wb.write(fos);
        } catch (IOException io) {
            logger.error(io.getMessage());
            logger.error(io.getStackTrace());
        }
    }

    public void setExcelRowData(int rowNum, int colNum,
                                String data) {
        try {
            Row row = ws.getRow(rowNum);
            Cell cel = row.createCell(colNum);
            cel.setCellType(CellType.STRING);
            cel.setCellValue(data);
            FileOutputStream fos = new FileOutputStream(fileName);
            wb.write(fos);
        } catch (IOException io) {
            logger.error(io.getMessage());
            logger.error(io.getStackTrace());
        }
    }

    public int getRowCount() {

        int rowCount = ws.getPhysicalNumberOfRows();
        return rowCount;
    }

    public int getColCount(int rowNum) {
        Row r = ws.getRow(rowNum);
        int colCount = r.getLastCellNum();
        return colCount;
    }

    public String getCell(int rowIndex, int columnIndex) {
        String cellValue = null;

        cellValue = ws.getRow(rowIndex).getCell(columnIndex).toString();
        return cellValue;
    }

    public String[][] ExcelInArray() {
        int noOfRows = ws.getPhysicalNumberOfRows();
        int noOfCol = ws.getRow(0).getLastCellNum();
        String data1[][] = new String[noOfRows][noOfCol];

        for (int i = 0; i < noOfRows; i++) {
            Row r = ws.getRow(i);
            for (int j = 0; j < noOfCol; j++) {
                Cell c = r.getCell(j);
                FormulaEvaluator evaluator = null;
                if (fileName.endsWith(".xlsx")) {
                    evaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
                } else if (fileName.endsWith(".xls")) {
                    evaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
                }
                DataFormatter df = new DataFormatter();
                data1[i][j] = df.formatCellValue(c, evaluator).trim();
            }
        }
        return data1;
    }

    public List<LinkedHashMap<String, String>> readExcelColumnWise() {
        String data[][] = ExcelInArray();
        int noOfRows = ws.getPhysicalNumberOfRows();
        int noOfCol = ws.getRow(0).getLastCellNum();
        LinkedHashMap<String, String> st1 = new LinkedHashMap<String, String>();
        List<LinkedHashMap<String, String>> listele = new ArrayList<LinkedHashMap<String, String>>();
        for (int i = 1; i < noOfCol; i++) {
            for (int j = 0; j < noOfRows; j++) {
                st1.put(data[j][0], data[j][i].trim());
            }
            listele.add(st1);
            st1 = new LinkedHashMap<String, String>();

        }
        return listele;
    }

    public List<HashMap<String, String>> readExcelRowWise() {
        String data[][] = ExcelInArray();
        int noOfRows = ws.getPhysicalNumberOfRows();
        int noOfCol = ws.getRow(0).getLastCellNum();
        HashMap<String, String> st1 = new HashMap<String, String>();
        List<HashMap<String, String>> listele = new ArrayList<HashMap<String, String>>();
        for (int i = 1; i < noOfRows; i++) {
            for (int j = 0; j < noOfCol; j++) {
                st1.put(data[0][j], data[i][j].trim());
            }
            listele.add(st1);
            st1 = new LinkedHashMap<String, String>();

        }
        return listele;
    }

}

