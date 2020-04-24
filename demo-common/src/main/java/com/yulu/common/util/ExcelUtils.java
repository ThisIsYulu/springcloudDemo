package com.yulu.common.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Yulu on 2020/4/24.
 */
public class ExcelUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public byte[] buildUserReportExcel(List<UserData> dataList) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            // 表名
            XSSFSheet sheet = wb.createSheet("User_Report");

            // 内容样式
            XSSFCellStyle contextStyle = getContextStyle(wb);
            // 标题样式
            XSSFCellStyle titleStyle = getTitleStyle(wb);

            // 大标题格式
            XSSFCellStyle cell1Style = wb.createCellStyle();
            cell1Style.setAlignment(HorizontalAlignment.CENTER);
            XSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);
            cell1Style.setFont(font);

            // 大标题内容
            XSSFRow row1 = sheet.createRow(0);
            XSSFCell cell = row1.createCell(0);
            cell.setCellValue("用户表");
            cell.setCellStyle(cell1Style);
            // 合并1-n列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

            // 数据列表标题
            XSSFRow rowForTitle = sheet.createRow(1);
            setCellValueAndStyle(rowForTitle, 0, "序号", titleStyle);
            setCellValueAndStyle(rowForTitle, 1, "名字", titleStyle);
            setCellValueAndStyle(rowForTitle, 2, "年龄", titleStyle);

            // 数据列表内容
            int rowNumber = 1;
            int indexNumber = 1;
            for (UserData data : dataList) {
                rowNumber += 1;
                XSSFRow rowForDetail = sheet.createRow(rowNumber);
                setCellValueAndStyle(rowForDetail, 0, data.getId(), contextStyle);
                setCellValueAndStyle(rowForDetail, 1, data.getName(), contextStyle);
                setCellValueAndStyle(rowForDetail, 2, String.valueOf(data.getAge()), contextStyle);

                indexNumber += 1;
            }

            // 设置每列的宽度
            sheet.setColumnWidth(0, 20 * 256);
            sheet.setColumnWidth(1, 20 * 256);
            sheet.setColumnWidth(2, 20 * 256);

            wb.write(outputStream);
        } catch (Exception e) {
            logger.error("buildUserReportExcel create excel error:", e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                logger.error("buildUserReportExcel close error:", e);
            }
        }
        return outputStream.toByteArray();
    }

    private void setCellValueAndStyle(XSSFRow row, int columnIndex, String cellValue, XSSFCellStyle cellStyle) {
        XSSFCell cell1 = row.createCell(columnIndex);
        cell1.setCellValue(cellValue);
        cell1.setCellStyle(cellStyle);
    }

    private XSSFCellStyle getTitleStyle(XSSFWorkbook workbook) {

        XSSFCellStyle style = workbook.createCellStyle();

        style.setAlignment(HorizontalAlignment.CENTER);//水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        style.setWrapText(true);//自动换行

        style.setBorderTop(BorderStyle.MEDIUM);//上边框
        style.setBorderBottom(BorderStyle.MEDIUM);//下边框
        style.setBorderLeft(BorderStyle.MEDIUM);//左边框
        style.setBorderRight(BorderStyle.MEDIUM);//右边框
        style.setTopBorderColor(new XSSFColor(Color.BLACK));//上边框颜色
        style.setBottomBorderColor(new XSSFColor(Color.BLACK));//下边框颜色
        style.setLeftBorderColor(new XSSFColor(Color.BLACK));//左边框颜色
        style.setRightBorderColor(new XSSFColor(Color.BLACK));//右边框颜色

        XSSFFont titleFont = workbook.createFont();//字体
        titleFont.setBold(true);
        style.setFont(titleFont);

        return style;
    }

    private XSSFCellStyle getContextStyle(XSSFWorkbook workbook) {

        XSSFCellStyle style = workbook.createCellStyle();

        style.setAlignment(HorizontalAlignment.CENTER);//水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        style.setWrapText(true);//自动换行

        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderBottom(BorderStyle.THIN);//下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setTopBorderColor(new XSSFColor(Color.BLACK));//上边框颜色
        style.setBottomBorderColor(new XSSFColor(Color.BLACK));//下边框颜色
        style.setLeftBorderColor(new XSSFColor(Color.BLACK));//左边框颜色
        style.setRightBorderColor(new XSSFColor(Color.BLACK));//右边框颜色

        return style;
    }
}
