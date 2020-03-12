package com.mtnz.util;

import com.itextpdf.text.pdf.Barcode128;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaiduPushUtil {


    public static void main(String[] args) {
        generateBarCode("11","11","111");
    }


    public void deleteFiles(String fileName){
        String files[]=fileName.split("uploadFiles");
        fileName=PathUtil.getClasspath()+"uploadFiles"+files[1];
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
        } else {
            if (file.isFile())
               deleteFile(fileName);
        }
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    public static String generateBarCode(String random,String product,String remark){
        try{
            int barCodeWidth=300;
            int barCodeHeight=180;
            int HEIGHT_SPACE = 0;

            //图片宽度
            int imageWidth = barCodeWidth;
            // 图片高度
            int imageHeight = barCodeHeight + HEIGHT_SPACE ;

            BufferedImage img = new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_INT_RGB);

            Graphics2D g = (Graphics2D) img.getGraphics();
            g.fillRect(0, 0, imageWidth, imageHeight);

            Font font = new java.awt.Font("", java.awt.Font.PLAIN, 20);
            Font fonts = new java.awt.Font("", java.awt.Font.PLAIN, 15);


            Barcode128 barcode128 = new Barcode128();

            FontRenderContext fontRenderContext = g.getFontRenderContext();

            //条形码（文字）的高度
            //int stringHeight = (int) font.getStringBounds("",fontRenderContext).getHeight();
            // 图片横坐标开始位置
            int startX = barCodeWidth/4;
            // 图片纵坐标开始位置
            int imageStartY = barCodeHeight-(barCodeHeight/2)+15;


            int stringStartY = barCodeHeight-(barCodeHeight/8)+15;// 条形码（文字）开始位置



            int codeWidth = (int) font.getStringBounds(random, fontRenderContext).getWidth();
            barcode128.setCode(random);
            java.awt.Image codeImg = barcode128.createAwtImage(Color.black, Color.white);

            g.drawImage(codeImg, startX, imageStartY, barCodeWidth/2, barCodeHeight/4, Color.white, null);



            //为图片添加条形码（文字），位置为条形码图片的下部居中
            AttributedString ats = new AttributedString(random);
            ats.addAttribute(TextAttribute.FONT, font, 0, random.length());
            AttributedCharacterIterator iter = ats.getIterator();


            AttributedString ats1 = new AttributedString(product);
            ats1.addAttribute(TextAttribute.FONT, fonts, 0, product.length());
            AttributedCharacterIterator iter1 = ats1.getIterator();


            AttributedString ats2 = new AttributedString(remark);
            ats2.addAttribute(TextAttribute.FONT, fonts, 0, remark.length());
            AttributedCharacterIterator iter2 = ats2.getIterator();


            // 设置条形码（文字）的颜色
            g.setColor(Color.BLACK);
            // 绘制条形码（文字）
            g.drawString(iter, (barCodeWidth/2)-(codeWidth/2), stringStartY);
            //g.drawString(iter, startX + (barCodeWidth - codeWidth) / 2, stringStartY);

            g.drawString(iter1, (barCodeWidth/4), barCodeHeight-(barCodeHeight/2)-20);

            g.drawString(iter2, (barCodeWidth/4), barCodeHeight-(barCodeHeight/2)+5);
            g.dispose();



            ffile();

            String ffile = UuidUtil.get32UUID() + ".jpg";
            String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG +DateUtil.getDays()+"/"+ ffile;
            String  product_img = Const.SERVERPATH + Const.FILEPATHIMG +DateUtil.getDays()+"/"+ ffile;


            ImageIO.write(img, "png",new File(filePath));

            return product_img;
        }catch (Exception e){
            return "false";
        }
    }


    public static void ffile(){
        String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG  + DateUtil.getDays(); // 文件上传路径
        File file = new File(filePath2, filePath2);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
    }

    public static String random(String store_id){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSS");
        String  formDate =sdf.format(date);
        String no = formDate.substring(10);
        return store_id+no;
    }



}
