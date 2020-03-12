package org.jeecgframework.web.hrm.service.impl;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jeecgframework.web.hrm.utils.DateUtil;
import org.jeecgframework.web.system.pojo.base.TSReimDetails;
import org.jeecgframework.web.system.pojo.base.TSReimburse;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @authortangzhen
 * @createDate2019-07-20
 */
@Service("reimExcelService")
public class ReimExcelService {

    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private String bDate;
    private int year;

    private void createRow(HSSFCellStyle style, int height, String value, int row1, int row2, int col1, int col2) {

        sheet.addMergedRegion(new CellRangeAddress(row1, row2, col1, col2));  //设置从第row1行合并到第row2行，第col1列合并到col2列
        HSSFRow rows = sheet.createRow(row1);        //设置第几行
        rows.setHeight((short) height);              //设置行高
        HSSFCell cell = rows.createCell(col1);       //设置内容开始的列
        HSSFCell c2 = rows.createCell(col1+1);
        HSSFCell c3 = rows.createCell(col1+2);
        HSSFCell c4 = rows.createCell(col1+3);
        HSSFCell c5 = rows.createCell(col1+4);
        c2.setCellStyle(style);
        c3.setCellStyle(style);
        c4.setCellStyle(style);
        c5.setCellStyle(style);
        cell.setCellStyle(style);                    //设置样式
        cell.setCellValue(value);                    //设置该行的值
    }
    private void createRow1(HSSFCellStyle style, int height, String value1,String value2, int row1, int row2, int col1, int col2,int col3,int col4) {

        sheet.addMergedRegion(new CellRangeAddress(row1, row2, col1, col2));  //设置从第row1行合并到第row2行，第col1列合并到col2列
        sheet.addMergedRegion(new CellRangeAddress(row1, row2, col3, col4));
        HSSFRow rows = sheet.createRow(row1);        //设置第几行
        rows.setHeight((short) height);              //设置行高
        HSSFCell cell = rows.createCell(col1);       //设置内容开始的列
        HSSFCell c2 = rows.createCell(col1+1);
        HSSFCell c3 = rows.createCell(col1+2);
        cell.setCellStyle(style);                    //设置样式
        c2.setCellStyle(style);
        c3.setCellStyle(style);
        cell.setCellValue(value1);//设置该行的值
        HSSFCell cel2 = rows.createCell(col3);       //设置内容开始的列
        HSSFCell c4 = rows.createCell(col3+1);
        HSSFCell c5 = rows.createCell(col3+2);
        c4.setCellStyle(style);
        c5.setCellStyle(style);
        cel2.setCellStyle(style);                    //设置样式
        cel2.setCellValue(value2);//设置该行的值
        HSSFRow rows2 = sheet.createRow(row2);
        HSSFCell ce21 = rows2.createCell(col1);       //设置内容开始的列
        HSSFCell ce22 = rows2.createCell(col1+1);
        HSSFCell ce23 = rows2.createCell(col1+2);
        HSSFCell ce24 = rows2.createCell(col3);       //设置内容开始的列
        HSSFCell ce25 = rows2.createCell(col3+1);
        HSSFCell ce26 = rows2.createCell(col3+2);
        ce21.setCellStyle(style);
        ce22.setCellStyle(style);
        ce23.setCellStyle(style);
        ce24.setCellStyle(style);
        ce25.setCellStyle(style);
        ce26.setCellStyle(style);
    }
    private void createRow2(HSSFCellStyle style, int height, String title1,String title2,String value1,String value2, int row1, int row2, int col1, int col2,int col3,int col4,int titlecol1,int titlecol2) {

        sheet.addMergedRegion(new CellRangeAddress(row1, row2, col1, col2));  //设置从第row1行合并到第row2行，第col1列合并到col2列
        sheet.addMergedRegion(new CellRangeAddress(row1, row2, col3, col4));
        HSSFRow rows = sheet.createRow(row1);        //设置第几行
        rows.setHeight((short) height);              //设置行高
        HSSFCell celltitle1 = rows.createCell(titlecol1);//设置标题名
        celltitle1.setCellStyle(style);
        celltitle1.setCellValue(title1);
        HSSFCell celltitle2 = rows.createCell(titlecol2);
        celltitle2.setCellStyle(style);
        celltitle2.setCellValue(title2);
        HSSFCell cel1 = rows.createCell(col1);       //设置内容开始的列
        HSSFCell c1 = rows.createCell(col1+1);
        c1.setCellStyle(style);
        cel1.setCellStyle(style);                    //设置样式
        cel1.setCellValue(value1);
        HSSFCell cel2 = rows.createCell(col3);//设置内容开始的列
        HSSFCell c2 = rows.createCell(col3+1);
        c2.setCellStyle(style);
        cel2.setCellStyle(style);                    //设置样式
        cel2.setCellValue(value2);             //设置该行的值
    }

    private void createDetailsRow(HSSFCellStyle style, int height, String value1,String value2,String value3, int row1, int row2, int col1, int col2) {

        sheet.addMergedRegion(new CellRangeAddress(row1, row2, col1, col2));  //设置从第row1行合并到第row2行，第col1列合并到col2列
        HSSFRow rows = sheet.createRow(row1);        //设置第几行
        rows.setHeight((short) height);              //设置行高
        HSSFCell cel1 = rows.createCell(col1-2);
        cel1.setCellStyle(style);
        cel1.setCellValue(value1);
        HSSFCell cel2 = rows.createCell(col1-1);
        cel2.setCellStyle(style);
        cel2.setCellValue(value2);
        HSSFCell cel3 = rows.createCell(col1);
        HSSFCell c2 = rows.createCell(col1+1);
        HSSFCell c3 = rows.createCell(col1+2);
        cel3.setCellStyle(style);
        c2.setCellStyle(style);
        c3.setCellStyle(style);
        cel3.setCellValue(value3);
    }


    /**
     * 创建样式
     *
     * @param fontSize 字体大小
     * @param align    水平位置  左右居中2 居右3 默认居左 垂直均为居中
     * @param bold     是否加粗
     * @return
     */
    private HSSFCellStyle getStyle(int fontSize, int align, boolean bold, boolean border) {
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) fontSize);// 字体大小
        if (bold) {
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);                         //设置字体
        style.setAlignment((short) align);          // 左右居中2 居右3 默认居左
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中1
        style.setWrapText(true);
        if (border) {
            style.setBorderRight((short) 2);
            style.setBorderLeft((short) 2);
            style.setBorderBottom((short) 2);
            style.setBorderTop((short) 2);
            style.setLocked(true);
        }
        return style;
    }
    /**
     * 创建表头
     *

     */
    private void createHeadCell() {
        // 表头标题
        HSSFCellStyle titleStyle = getStyle(18, 2, true, true);//样式
        createRow1(titleStyle, 0x449, "","EXPENSES CLAIM", 0, 0, 0,2,3,5);
        //第二行
        HSSFCellStyle titleStyle2 = getStyle(16, 1, true, true);
        createRow1(titleStyle, 0x499, "重庆新钶电子有限公司 ","EXPENSES CLAIM\r\n费用报销单", 0, 1, 0,2,3,5);

    }

    private void setCellData(TSUser user,TSReimburse tsReimburse, List<TSReimDetails> tsReimDetailsList,String projectName1,String projectName2) {
        // 创建数据
        HSSFRow row = null;
        HSSFCell cell = null;
        //3-8行
        HSSFCellStyle dataStyle1 = getStyle(10, 1, false, true);
        createRow2(dataStyle1, 0x209, "姓名", "工号", user.getUserName(), user.getJobNum(), 2, 2, 1, 2, 4, 5, 0, 3);
        createRow2(dataStyle1, 0x209, "部门", "录入日期", user.getDepartment(), DateUtil.formatDate(tsReimburse.getApplyDate().getTime()), 3, 3, 1, 2, 4, 5, 0, 3);
        createRow2(dataStyle1, 0x209, "报销支出项目", "报销支出项目编号", tsReimburse.getExpendName(), projectName2, 4, 4, 1, 2, 4, 5, 0, 3);
//        createRow2(dataStyle1, 0x209, "实际产生项目编号", "报销支出项目编号", projectName1, projectName2, 5, 5, 1, 2, 4, 5, 0, 3);
        String startTime="";
        String endTime="";
        if(tsReimburse.getStartTime()!=null){
            startTime=DateUtil.formatDateTime2(tsReimburse.getStartTime().getTime());
        }
        if(tsReimburse.getEndTime()!=null){
            endTime=DateUtil.formatDateTime2(tsReimburse.getEndTime().getTime());
        }
        createRow2(dataStyle1, 0x209, "出差开始日期", "出差结束日期", startTime, endTime, 5, 5, 1, 2, 4, 5, 0, 3);
        createRow2(dataStyle1, 0x209, "成本中心号", "项目", tsReimburse.getCostCenterCode(), tsReimburse.getExpendName(), 6, 6, 1, 2, 4, 5, 0, 3);
        createRow2(dataStyle1, 0x209, "出差地点", "货币类型", tsReimburse.getCountryName(), tsReimburse.getCurrency(), 7, 7, 1, 2, 4, 5, 0, 3);

        //设置 明细 9行 表头
        HSSFCellStyle detailsHead = getStyle(12, 2, true, true);
        createDetailsRow(detailsHead, 0x229, "日期", "费用类型", "简述", 8, 8, 2, 4);
        HSSFRow rows9 = sheet.getRow(8);
        HSSFCell cel4 = rows9.createCell(5);       //设置内容开始的列
        cel4.setCellStyle(detailsHead);                    //设置样式
        cel4.setCellValue("金额");//设置该行的值
        //表格data 10行开始
        HSSFCellStyle detailsdata = getStyle(10, 1, false, true);
        HSSFCellStyle detailsdata2 = getStyle(10, 3, false, true);
        TSReimDetails t = null;
        int r = 9;
        double totalAmount = 0.0;
        for (int i = 0, le = tsReimDetailsList.size(); i < le; i++) {
            t = tsReimDetailsList.get(i);
            createDetailsRow(detailsdata, 0x209, DateUtil.formatDate(t.getCreateDate().getTime()), typeIntToString(t.getExpenseType()), t.getDetails(), r, r, 2, 4);
            HSSFRow dataRow = sheet.getRow(r);
            HSSFCell hssfCell = dataRow.createCell(5);       //设置内容开始的列
            hssfCell.setCellStyle(detailsdata2);                    //设置样式
            hssfCell.setCellValue(t.getCostFee().doubleValue());//设置该行的值
            totalAmount = totalAmount + t.getCostFee().doubleValue();
            r++;
        }
//        totalAmount = totalAmount + tsReimburse.getAllowanceFee().doubleValue();//总金额
        //
//        createRow(detailsdata2,0x209,"补贴金额", r, r, 0, 4);
//        HSSFRow allowanceRow = sheet.getRow(r);        //设置第几行
//        HSSFCell allowanceRowCell = allowanceRow.createCell(5);       //设置内容开始的列
//        allowanceRowCell.setCellStyle(detailsdata2);                    //设置样式
//        allowanceRowCell.setCellValue(tsReimburse.getAllowanceFee().doubleValue());                    //设置该行的值

        //预留行位
        int m = 0;
        while (m < 5) {
            createDetailsRow(detailsdata, 0x209, "", "", "", r, r, 2, 4);
            HSSFRow dataRow = sheet.getRow(r);
            HSSFCell hssfCell = dataRow.createCell(5);       //设置内容开始的列
            hssfCell.setCellStyle(detailsdata2);                    //设置样式
            hssfCell.setCellValue("");//设置该行的值
            m++;
            r++;
        }

        //总金额
        createRow(detailsdata2, 0x209, "Sub Total", r, r, 0, 4);
        HSSFRow total = sheet.getRow(r);        //设置第几行
        HSSFCell totalCell = total.createCell(5);       //设置内容开始的列
        totalCell.setCellStyle(detailsdata2);                    //设置样式
        totalCell.setCellValue(totalAmount);
        r++;

        //打印日期
        sheet.addMergedRegion(new CellRangeAddress(r, r, 2, 4));
        HSSFRow tailRow1 = sheet.createRow(r);
        tailRow1.setHeight((short) 0X209);
        HSSFCell[] arr = coverToArr(tailRow1, 6, detailsdata);
        arr[0].setCellValue("系统打印日期");
        arr[1].setCellValue(DateUtil.formatDate(System.currentTimeMillis()));
        r++;
//        HSSFCell tailcell1=tailRow1.createCell(0);
//        tailcell1.setCellStyle(detailsdata);
//        tailcell1.setCellValue("系统打印日期");
//        HSSFCell tailcell12=tailRow1.createCell(2);
//        tailcell12.setCellStyle(detailsdata);
//        tailcell12.setCellValue(DateUtil.formatDate(System.currentTimeMillis()));


        //尾部签名
        sheet.addMergedRegion(new CellRangeAddress(r, r, 3, 5));
        HSSFRow tailRow2 = sheet.createRow(r);
        tailRow2.setHeight((short) 0X309);
        HSSFCell[] arr2 = coverToArr(tailRow2, 6, detailsdata);
        arr2[0].setCellValue("Claimed By：");
        arr2[1].setCellValue(user.getUserName());
        arr2[2].setCellValue("Approved By：");
        r++;
//        HSSFCell tailcell2=tailRow2.createCell(0);
//        tailcell2.setCellStyle(detailsdata);
//        tailcell2.setCellValue("Claimed By：");
//        HSSFCell tailcell22=tailRow2.createCell(1);
//        tailcell22.setCellStyle(detailsdata);
//        tailcell22.setCellValue(user.getUserName());
//        HSSFCell tailcell23=tailRow2.createCell(2);
//        tailcell23.setCellStyle(detailsdata);
//        tailcell23.setCellValue("Approved By：");

        sheet.addMergedRegion(new CellRangeAddress(r, r, 3, 5));
        HSSFRow tailRow3 = sheet.createRow(r);
        tailRow3.setHeight((short) 0X309);
        HSSFCell[] arr3 = coverToArr(tailRow3, 6, detailsdata);
        arr3[0].setCellValue("Staff");
        arr3[2].setCellValue("Project/Dept Manager");
        r++;
//        HSSFCell tailcell3=tailRow3.createCell(0);
//        tailcell3.setCellStyle(detailsdata);
//        tailcell3.setCellValue("Staff");
//        HSSFCell tailcell33=tailRow3.createCell(2);
//        tailcell33.setCellStyle(detailsdata);
//        tailcell33.setCellValue("Project/Dept Manager");

    }
    public InputStream createExcelFile(TSUser user,TSReimburse tsReimburse, List<TSReimDetails> tsReimDetailsList,String projectName1,String projectName2) throws IOException, IOException {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("费用报销单");
        for(int i=0;i<6;i++){
            if(i==2||i==3){
                sheet.setColumnWidth(i, 30*256);
            }else {
                sheet.setColumnWidth(i, 20*256);
            }
        }
        // 创建表头 startRow代表表体开始的行
         createHeadCell();
        // 创建表体数据
        setCellData(user, tsReimburse, tsReimDetailsList,projectName1,projectName2);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        sheet.protectSheet(UUID.randomUUID().toString());
        workbook.write(baos);
        byte[] ba = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        return bais;
    }


    public String  typeIntToString(int type) {
        String str = "";
        switch (type) {
            case 1:
                str = "交通";
                break;
            case 2:
                str = "酒店";
                break;
            case 3:
                str = "通讯";
                break;
            case 4:
                str = "补贴";
                break;
            default:
                str = "其它";
        }
        return str;
    }
    public HSSFCell[] coverToArr(HSSFRow hssfRow,int arg,HSSFCellStyle style){
        HSSFCell[] arr=new HSSFCell[arg];
        HSSFCell hssfCell=null;
        for(int i=0;i<arg;i++){
            hssfCell=hssfRow.createCell(i);
            hssfCell.setCellStyle(style);
            arr[i]=hssfCell;
        }
        return arr;
    }
}
