package com.mtnz.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.Region;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/*
    Created by xxj on 2018\7\9 0009.  
 */
public class AdminExtend {
    /**
     * @param fileName      文件名称
     * @param headers       表头
     * @param dataset       数据集
     * @param isSortDataSet 是否对数据排序
     * @param response      HttpServletResponse
     * @param mergeBasis    合并基准列 可选
     * @param mergeCells    要合并的列 可选
     * @param sumCells      要求和的列 可选
     * @param timeCells     时间列 可选
     * @throws IOException
     */
    public static void exportExelMerge(String fileName, final String[] headers, List<String[]> dataset, boolean isSortDataSet, HttpServletResponse response, final Integer[] mergeBasis, final Integer[] mergeCells, final Integer[] sumCells, final Integer[] timeCells) throws IOException {
        String title = "Sheet1";
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));

        createExcelMerge(title, headers, dataset, isSortDataSet, response.getOutputStream(), mergeBasis, mergeCells, sumCells, timeCells);

        response.setStatus(HttpServletResponse.SC_OK);
        response.flushBuffer();
    }

    /**
     * @param title         文件名称
     * @param headers       表头
     * @param dataset       数据集
     * @param isSortDataSet 是否对数据排序
     * @param out           OutputStream
     * @param mergeBasis    合并基准列 可选
     * @param mergeCells    要合并的列
     * @param sumCells      要求和的列
     * @param timeCells     时间列 可选
     */
    public static void createExcelMerge(String title, final String[] headers, List<String[]> dataset, boolean isSortDataSet, OutputStream out, final Integer[] mergeBasis, final Integer[] mergeCells, final Integer[] sumCells, final Integer[] timeCells) {


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);



        sheet.setDefaultColumnWidth(15); // 设置表格默认列宽度为15个字节

        HSSFCellStyle headStyle = createHeadStyle(workbook); // 生成头部样式
        HSSFCellStyle commonDataStyle = createCommonDataStyle(workbook); // 生成一般数据样式
        HSSFCellStyle numStyle = createNumStyle(workbook); //生成数字类型保留两位小数样式
        HSSFCellStyle sumRowStyle = createSumRowStyle(workbook); //生成合计行样式

        if (headers == null || headers.length <= 0) {
            return;
        }

        HSSFRow row = sheet.createRow(0); // 产生表格标题行
        /*for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row1.createCell(i);
            cell.setCellStyle(headStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }*/
        HSSFCellStyle style = workbook.createCellStyle(); // 样式对象?
        /*style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);*/
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //创建单元格
        HSSFCell c20 = row.createCell(0);
        c20.setCellValue(new HSSFRichTextString("订单号"));
        c20.setCellStyle(style);

        HSSFCell c0 = row.createCell(1);
        c0.setCellValue(new HSSFRichTextString("客户名称"));
        c0.setCellStyle(style);

        HSSFCell c3 = row.createCell(2);
        c3.setCellValue(new HSSFRichTextString("客户电话"));
        c3.setCellStyle(style);

        HSSFCell c4 = row.createCell(3);
        c4.setCellValue(new HSSFRichTextString("客户地址"));
        c4.setCellStyle(style);

        HSSFCell c1 = row.createCell(4);
        c1.setCellValue(new HSSFRichTextString("店铺名称"));
        c1.setCellStyle(style);

        HSSFCell c7 = row.createCell(5);
        c7.setCellValue(new HSSFRichTextString("开单人"));
        c7.setCellStyle(style);

        HSSFCell c2 = row.createCell(6);
        c2.setCellValue(new HSSFRichTextString("商品明细"));
        c2.setCellStyle(style);

        /*HSSFCell c3 = row.createCell(11);
        c3.setCellValue(new HSSFRichTextString("合计金额"));
        c3.setCellStyle(style);*/

        /*HSSFCell c4 = row.createCell(12);
        c4.setCellValue(new HSSFRichTextString("结算方式"));
        c4.setCellStyle(style);*/

/*        HSSFCell c5 = row.createCell(13);
        c5.setCellValue(new HSSFRichTextString("实收"));
        c5.setCellStyle(style);*/

        /*HSSFCell c6 = row.createCell(14);
        c6.setCellValue(new HSSFRichTextString("欠款"));
        c6.setCellStyle(style);*/

        /*HSSFCell c7 = row.createCell(13);
        c7.setCellValue(new HSSFRichTextString("余欠"));
        c7.setCellStyle(style);

        HSSFCell c8 = row.createCell(14);
        c8.setCellValue(new HSSFRichTextString("开单人"));
        c8.setCellStyle(style);*/

        HSSFCell c9 = row.createCell(11);
        c9.setCellValue(new HSSFRichTextString("开单时间"));
        c9.setCellStyle(style);
        row = sheet.createRow(1);
        HSSFCell cc1 = row.createCell(5);
        cc1.setCellValue(new HSSFRichTextString("商品名称"));
        cc1.setCellStyle(style);

        HSSFCell cc2 = row.createCell(6);
        cc2.setCellValue(new HSSFRichTextString("规格单位"));
        cc2.setCellStyle(style);

        /*HSSFCell cc3 = row.createCell(5);
        cc3.setCellValue(new HSSFRichTextString("单价"));
        cc3.setCellStyle(style);*/

        HSSFCell cc4 = row.createCell(7);
        cc4.setCellValue(new HSSFRichTextString("单价单位"));
        cc4.setCellStyle(style);

        HSSFCell cc5 = row.createCell(8);
        cc5.setCellValue(new HSSFRichTextString("销售数量"));
        cc5.setCellStyle(style);

        HSSFCell cc6 = row.createCell(8);
        cc6.setCellValue(new HSSFRichTextString("销售单位"));
        cc6.setCellStyle(style);

/*        HSSFCell cc7 = row.createCell(9);
        cc7.setCellValue(new HSSFRichTextString("单品总价"));
        cc7.setCellStyle(style);*/

        // 四个参数分别是：起始行，起始列，结束行，结束列?
        Region region20 = new Region(0, (short) 0, 1, (short) 0);
        Region region0 = new Region(0, (short) 1, 1, (short) 1);
        Region region5 = new Region(0, (short) 2, 1, (short) 2);
        Region region1 = new Region(0, (short) 3, 1, (short) 3);
        Region region11 = new Region(0, (short) 4, 1, (short) 4);
        Region region4 = new Region(0, (short) 5, 1, (short) 5);
        Region region2 = new Region(0, (short) 6, 0, (short) 10);
        Region region3 = new Region(0, (short) 11, 1, (short) 11);
        //Region region5 = new Region(0, (short) 13, 1, (short) 13);
        //Region region6 = new Region(0, (short) 14, 1, (short) 14);
        //Region region7 = new Region(0, (short) 15, 1, (short) 15);
        /*Region region8 = new Region(0, (short) 15, 1, (short) 15);
        Region region9 = new Region(0, (short) 16, 1, (short) 16);*/
        sheet.addMergedRegion(region0);
        sheet.addMergedRegion(region20);
        sheet.addMergedRegion(region1);
        sheet.addMergedRegion(region2);
        sheet.addMergedRegion(region3);
        sheet.addMergedRegion(region4);
        sheet.addMergedRegion(region5);
        //sheet.addMergedRegion(region6);
        //sheet.addMergedRegion(region7);
        sheet.addMergedRegion(region11);
        /*sheet.addMergedRegion(region8);
        sheet.addMergedRegion(region9);*/

       /* int rowNum = 0;
        HSSFRow row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("客户名称");
        row.createCell(1).setCellValue("手机号");
        row.createCell(2).setCellValue("商品明细");
        row.createCell(9).setCellValue("合计金额");
        row.createCell(10).setCellValue("结算方式");
        row.createCell(11).setCellValue("实收");
        row.createCell(12).setCellValue("优惠");
        row.createCell(13).setCellValue("余欠");
        row.createCell(14).setCellValue("开单人");
        row.createCell(15).setCellValue("开单时间");
        row = sheet.createRow(rowNum++);
        row.createCell(2).setCellValue("商品名称");
        row.createCell(3).setCellValue("规格单位");
        row.createCell(4).setCellValue("单价");
        row.createCell(5).setCellValue("单价单位");
        row.createCell(6).setCellValue("销售数量");
        row.createCell(7).setCellValue("销售单位");
        row.createCell(8).setCellValue("单品总价");
        sheet.addMergedRegion(new CellRangeAddress(0,0,2,8));
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
        sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
        sheet.addMergedRegion(new CellRangeAddress(0,1,9,9));
        sheet.addMergedRegion(new CellRangeAddress(0,1,10,10));
        sheet.addMergedRegion(new CellRangeAddress(0,1,11,11));
        sheet.addMergedRegion(new CellRangeAddress(0,1,12,12));
        sheet.addMergedRegion(new CellRangeAddress(0,1,13,13));
        sheet.addMergedRegion(new CellRangeAddress(0,1,14,14));
        sheet.addMergedRegion(new CellRangeAddress(0,1,15,15));
*/
        /*if (isSortDataSet && mergeBasis != null && mergeBasis.length > 0) { //是否排序数据
            Collections.sort(dataset, new Comparator<String[]>() {
                public int compare(String[] o1, String[] o2) {
                    String s1 = "";
                    String s2 = "";
                    for (int i = 0; i < mergeBasis.length; i++) {
                        s1 += (o1[mergeBasis[i].intValue()] + Character.valueOf((char) 127).toString());
                        s2 += (o2[mergeBasis[i].intValue()] + Character.valueOf((char) 127).toString());
                    }
                    if (timeCells != null && timeCells.length > 0) {
                        for (int i = 0; i < timeCells.length; i++) {
                            s1 += o1[timeCells[i].intValue()];
                            s2 += o2[timeCells[i].intValue()];
                        }
                    }
                    if (s1.compareTo(s2) < 0) {
                        return -1;
                    } else if (s1.compareTo(s2) == 0) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        }*/

/*
        int height=0;
        List <Object>list = new ArrayList<Object>();
       // TestExcel.Pojo.Shangpin sp = null;
        for(int i=0;i<7;i++){
            height = 7;
            row = sheet.createRow(rowNum++);
            sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum+height,0,0));
            sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum+height,1,1));
            sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum+height,9,9));
            sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum+height,10,10));
            sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum+height,11,11));
            sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum+height,12,12));
            sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum+height,13,13));
            sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum+height,14,14));
            sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum+height,15,15));
            row.createCell(0).setCellValue("1");
            row.createCell(1).setCellValue("2");
            row.createCell(2).setCellValue("3");
            row.createCell(3).setCellValue("4");
            row.createCell(4).setCellValue("5");
            row.createCell(5).setCellValue("6");
            row.createCell(6).setCellValue("7");
            row.createCell(7).setCellValue("8");
            row.createCell(8).setCellValue("9");
            row.createCell(9).setCellValue("0");
            row.createCell(10).setCellValue("11");
            row.createCell(11).setCellValue("12");
            row.createCell(12).setCellValue("13");
            row.createCell(13).setCellValue("14");
            row.createCell(14).setCellValue("15");
            row.createCell(15).setCellValue("16");*/
        /*    for(int i = 1;i<entity.getSpList().size();i++){
                sp = entity.getSpList().get(i);
                row = sheet.createRow(rowNum++);
                row.createCell(2).setCellValue(sp.getName());
                row.createCell(3).setCellValue(sp.getDanwei());
                row.createCell(4).setCellValue(sp.getPrice());
                row.createCell(5).setCellValue(sp.getQuan());
                row.createCell(6).setCellValue(sp.getNum());
                row.createCell(7).setCellValue(sp.getUnit());
                row.createCell(8).setCellValue(sp.getMoney());
            }*/
        // }
        // 遍历集合数据，产生数据行
        Iterator<String[]> it = dataset.iterator();
        int index = 0;
        List <String[]> list = new ArrayList<String[]>();
        while (it.hasNext()) {
            // row = sheet.createRow(index);
            String[] dataSources = it.next();
            String [] a=  new String[dataSources.length];
            for (int i = 0; i < dataSources.length; i++) {
                a[i]=dataSources[i];
            }
            list.add(a);
            /*for (int i = 0; i < dataSources.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(commonDataStyle);
                    cell.setCellValue(dataSources[i]);
            }*/
        }
        for(int i =0;i<list.size();i++){
            if("".equals(list.get(i)[0])){
                String [] temp = list.get(i);
                list.remove(i);
                list.add(0, temp);
            }
        }
        for(String[]b:list){
            index++;
            row = sheet.createRow(index);
            for(int k=0;k<b.length;k++){
                HSSFCell cell = row.createCell(k);
                cell.setCellStyle(commonDataStyle);
                if(!"".equals(b[k])){
                    cell.setCellValue(b[k]);
                }
                //System.out.println(b[k]);
            }
        }
        try {
            if (mergeBasis != null && mergeBasis.length > 0 && mergeCells != null && mergeCells.length > 0) {
                for (int i = 0; i < mergeCells.length; i++) {
                    mergedRegion(sheet, mergeCells[i], 1, sheet.getLastRowNum(), workbook, mergeBasis);
                }
            }
            if (sumCells != null && sumCells.length > 0) {
                createSumRow(sheet, row, headers, sumCells, sumRowStyle, numStyle);
            }
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建合计行
     *
     * @param sheet
     * @param row
     * @param headers
     * @param sumCells
     * @param sumRowStyle
     * @param numStyle
     */
    private static void createSumRow(HSSFSheet sheet, HSSFRow row, final String[] headers, final Integer[] sumCells, HSSFCellStyle sumRowStyle, HSSFCellStyle numStyle) {
        row = sheet.createRow(sheet.getLastRowNum() + 1);
        for (int i = 1; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(sumRowStyle);
        }
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            for (int j = 0; j < sumCells.length; j++) {
                sheet.getRow(i).getCell(sumCells[j]).setCellValue(Double.parseDouble(sheet.getRow(i).getCell(sumCells[j]).getStringCellValue()));
                sheet.getRow(i).getCell(sumCells[j]).setCellStyle(numStyle);
            }
        }
        HSSFCell sumCell = row.getCell(0);
        sumCell.setCellValue("合计：");
        String sumFunctionStr = null;
        for (int i = 0; i < sumCells.length; i++) {
            sumFunctionStr = "SUM(" + CellReference.convertNumToColString(sumCells[i]) + "2:" + CellReference.convertNumToColString(sumCells[i]) + sheet.getLastRowNum() + ")";
            row.getCell(sumCells[i]).setCellFormula(sumFunctionStr);
        }
    }

    /**
     * 普通数据单元格样式
     *
     * @param workbook
     * @return
     */
    private static HSSFCellStyle createCommonDataStyle(HSSFWorkbook workbook) {
        //普通数据单元格样式
        HSSFCellStyle commonDataStyle = workbook.createCellStyle();
        /*  commonDataStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);*/
        /*commonDataStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        commonDataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        commonDataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        commonDataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        commonDataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);*/
        commonDataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        commonDataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //普通数据单元格字体
        HSSFFont commonDataFont = workbook.createFont();
        //commonDataFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        //把字体应用到当前的样式
        commonDataStyle.setFont(commonDataFont);
        return commonDataStyle;
    }

    /**
     * 合并单元格
     *
     * @param sheet
     * @param cellLine
     * @param startRow
     * @param endRow
     * @param workbook
     * @param mergeBasis
     */
    private static void mergedRegion(HSSFSheet sheet, int cellLine, int startRow, int endRow, HSSFWorkbook workbook, Integer[] mergeBasis) {
        HSSFCellStyle style = workbook.createCellStyle();           // 样式对象
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  // 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);             // 水平
        String s_will = sheet.getRow(startRow).getCell(cellLine).getStringCellValue();  // 获取第一行的数据,以便后面进行比较
        int count = 0;
        Set<Integer> set = new HashSet<Integer>();
        CollectionUtils.addAll(set, mergeBasis);
        for (int i = 2; i <= endRow; i++) {
            String s_current = sheet.getRow(i).getCell(cellLine).getStringCellValue();
            if (s_will.equals(s_current)) {
                boolean isMerge = true;
                if (!set.contains(cellLine)) {//如果不是作为基准列的列 需要所有基准列都相同
                    for (int j = 0; j < mergeBasis.length; j++) {
                        if (!sheet.getRow(i).getCell(mergeBasis[j]).getStringCellValue()
                                .equals(sheet.getRow(i - 1).getCell(mergeBasis[j]).getStringCellValue())) {
                            isMerge = false;
                        }
                    }
                } else {//如果作为基准列的列 只需要比较列号比本列号小的列相同
                    for (int j = 0; j < mergeBasis.length && mergeBasis[j] < cellLine; j++) {
                        if (!sheet.getRow(i).getCell(mergeBasis[j]).getStringCellValue()
                                .equals(sheet.getRow(i - 1).getCell(mergeBasis[j]).getStringCellValue())) {
                            isMerge = false;
                        }
                    }
                }
                if (isMerge) {
                    count++;
                } else {
                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow + count, cellLine, cellLine));
                    startRow = i;
                    s_will = s_current;
                    count = 0;
                }
            } else {
                sheet.addMergedRegion(new CellRangeAddress(startRow, startRow + count, cellLine, cellLine));
                startRow = i;
                s_will = s_current;
                count = 0;
            }
            if (i == endRow && count > 0) {
                sheet.addMergedRegion(new CellRangeAddress(startRow, startRow + count, cellLine, cellLine));
            }
        }
    }


    /**
     * 标题单元格样式
     *
     * @param workbook
     * @return
     */
    private static HSSFCellStyle createHeadStyle(HSSFWorkbook workbook) {
        //标题单元格样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        /*     headStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);*/
        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //标题单元格字体
        HSSFFont headFont = workbook.createFont();
        //headFont.setColor(HSSFColor.VIOLET.index);
        headFont.setFontHeightInPoints((short) 12);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        headStyle.setFont(headFont);
        return headStyle;
    }


    /**
     * 合计行单元格样式
     *
     * @param workbook
     * @return
     */
    private static HSSFCellStyle createSumRowStyle(HSSFWorkbook workbook) {
        //合计行单元格样式
        HSSFCellStyle sumRowStyle = workbook.createCellStyle();
        /* sumRowStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);*/
        sumRowStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        sumRowStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        sumRowStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        sumRowStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        sumRowStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        sumRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        sumRowStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        //合计行单元格字体
        HSSFFont sumRowFont = workbook.createFont();
        //sumRowFont.setColor(HSSFColor.VIOLET.index);
        sumRowFont.setFontHeightInPoints((short) 12);
        sumRowFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        sumRowStyle.setFont(sumRowFont);
        return sumRowStyle;
    }

    /**
     * 自定义保留两位小数数字单元格格式
     *
     * @param workbook
     * @return
     */
    private static HSSFCellStyle createNumStyle(HSSFWorkbook workbook) {
        //自定义保留两位小数数字单元格格式
        HSSFCellStyle numStyle = workbook.createCellStyle();
        /* numStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);*/
        numStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        numStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        numStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        numStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        numStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        numStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        numStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        numStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        //自定义保留两位小数数字单元格字体
        HSSFFont numFont = workbook.createFont();
        numFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        //把字体应用到当前的样式
        numStyle.setFont(numFont);
        return numStyle;
    }


    /**
     * 导出excle
     *
     * @param var          数据列表
     * @param downLoadPath 下载路径
     */
    public static void printExcle(List<PageData> var, String downLoadPath, HttpServletRequest request, HttpServletResponse response) {
        String[] headers = {"规格", "单价", "单位", "销售数量", "销售单位", "单品价", "合计", "结算", "实收", "时间","欠款"};
        List<String[]> dataset = new ArrayList<String[]>();
        dataset.add(new String[]{"", "", "","","","","商品", "规格",  "单价单位", "销售数量", "销售单位",  ""});
        for(int i=0;i<var.size();i++){
            dataset.add(new String[]{var.get(i).getString("order_number"),var.get(i).getString("name"),var.get(i).getString("phone"),var.get(i).getString("address"),var.get(i).getString("gname"),
                     var.get(i).getString("open_bill"),var.get(i).getString("product_name"),var.get(i).getString("norms1")+
                    var.get(i).getString("norms2")+"/"+var.get(i).getString("norms3"),
                    "元/"+var.get(i).getString("norms3"),
                    var.get(i).get("num").toString(),var.get(i).getString("norms3")
                    ,var.get(i).getString("date")});
        }
        try {
            Date date = new Date();
            String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
            OutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
            AdminExtend.createExcelMerge(filename + ".xls", headers, dataset, true, out, new Integer[]{0,1,2 ,3,4,5,10}, new Integer[]{0, 1,2,3,4,5,10}, new Integer[]{}, new Integer[]{});
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String[] headers = {"规格", "单价", "单位", "销售数量", "销售单位", "单品价", "时间"};
        List<String[]> dataset = new ArrayList<String[]>();
        dataset.add(new String[]{"", "", "", "商品", "规格", "单价", "单价单位", "销售数量", "销售单位", "单品总价", ""});
        dataset.add(new String[]{"123456", "王五", "123456789", "欣喜", "100 粒/瓶", "6.00000", "元/L", "1.000000", "瓶", "56", "3792", "现金", "122.00000","2018-06-07"});
        dataset.add(new String[]{"123456", "王五", "123456789", "欣喜", "100 粒/瓶", "6.00000", "元/L", "1.000000", "瓶", "56", "3792", "现金", "122.00000","2018-06-07"});
        dataset.add(new String[]{"123456", "王五", "123456789", "欣喜", "100 粒/瓶", "6.00000", "元/L", "1.000000", "瓶", "56", "3792", "现金", "122.00000","2018-06-07"});
        dataset.add(new String[]{"123456", "王五", "123456789", "欣喜", "100 粒/瓶", "6.00000", "元/L", "1.000000", "瓶", "56", "3792", "现金", "122.00000","2018-06-07"});
        dataset.add(new String[]{"123456", "王五", "123456789", "欣喜", "100 粒/瓶", "6.00000", "元/L", "1.000000", "瓶", "56", "3792", "现金", "122.00000","2018-06-07"});
        dataset.add(new String[]{"123456", "王五", "123456789", "欣喜", "100 粒/瓶", "6.00000", "元/L", "1.000000", "瓶", "56", "3792", "现金", "122.00000","2018-06-07"});
        dataset.add(new String[]{"123456", "王五", "123456789", "欣喜", "100 粒/瓶", "6.00000", "元/L", "1.000000", "瓶", "56", "3792", "现金", "122.00000","2018-06-07"});
        dataset.add(new String[]{"123457", "王七", "123456789", "欣喜", "100 粒/瓶", "6.00000", "元/L", "1.000000", "瓶", "56", "3792", "现金", "122.00000","2018-06-07"});
        dataset.add(new String[]{"123458", "王八", "123456789", "欣喜", "100 粒/瓶", "6.00000", "元/L", "1.000000", "瓶", "56", "3792", "现金", "122.00000","2018-06-07"});
      /*  dataset.add(new String[] {"2018-06-05","123456789","磷酸","10 Kg/袋", "20.00000", "元/L", "1.000000", "瓶","56","3792","欠款","0.00000","刘麻子"});
        dataset.add(new String[] {"2018-06-05","123456789","欣喜","5 Kg/袋", "20.00000", "元/L", "1.000000", "瓶","56","3792","欠款","0.00000","刘麻子"});
        dataset.add(new String[] {"2018-06-05","123456789","喷一喷","5 Kg/袋", "20.00000", "元/L", "1.000000", "瓶","56","3792","欠款","0.00000","刘麻子"});
        dataset.add(new String[] {"2018-06-01","123456789","磷酸","100 粒/包", "60.00000", "元/L", "1.000000", "瓶","56","56","现金","56.00000","张三"});
        dataset.add(new String[] {"2018-06-01","123456789","欣喜","4L/瓶", "56.00000", "元/L", "1.000000", "瓶","56","56","现金","56.00000","张三"});
        dataset.add(new String[] {"2018-05-27","123456789","欣喜","4L/瓶", "56.00000", "元/L", "1.000000", "瓶","56","56","现金","56.00000","张三"});
        dataset.add(new String[] {"2018-05-27","123456789","欣喜","4L/瓶", "56.00000", "元/L", "1.000000", "瓶","56","56","现金","56.00000","张三"});
*/

        try {
            OutputStream out = new FileOutputStream("E://ceshi.xls");
            AdminExtend.createExcelMerge("测试.xls", headers, dataset, true, out, new Integer[]{0, 1, 2}, new Integer[]{0, 1, 2, 10, 11}, new Integer[]{}, new Integer[]{11});
            out.close();
            JOptionPane.showMessageDialog(null, "导出成功!");
            System.out.println("excel导出成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Data {
        private String name;
        private String orderNum;

    }
}
