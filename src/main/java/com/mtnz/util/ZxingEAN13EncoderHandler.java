package com.mtnz.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/*
    Created by xxj on 2018\5\26 0026.  
 */
public class ZxingEAN13EncoderHandler {

    /**
     * 编码
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     */
    /*public void encode(String contents, int width, int height, String imgPath) {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.EAN_13, codeWidth, height, null);

            MatrixToImageWriter
                    .writeToFile(bitMatrix, "png", new File(imgPath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * @param args
     */
    /*public static void main(String[] args) {
        String imgPath = "barcode3.png";
        // 益达无糖口香糖的条形码
        //String contents = "6923450657713";

        String contentss = "690123456789";

        int width = 105, height = 50;
        ZxingEAN13EncoderHandler handler = new ZxingEAN13EncoderHandler();
        handler.encode(contentss, width, height, imgPath);

        System.out.println("Michael ,you have finished zxing EAN13 encode.");
    }*/


    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private static BufferedImage toBufferedImage(BitMatrix bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, bm.get(i, j) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void writeBitMatricToFile(BitMatrix bm, String format,
                                            File file) {
        BufferedImage image = toBufferedImage(bm);
        try {
            if (!ImageIO.write(image, format, file)) {
                throw new RuntimeException("Can not write an image to file" + file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToStream(BitMatrix matrix, String format,
                                     OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }


    public static void main(String[] args) throws Exception {
        int width = 300;
        int height = 50;
        // int width = 105;
        // int height = 50;
        // 条形码的输入是13位的数字
        // String text = "6923450657713";
        // 二维码的输入是字符串
        String text = "6923450657713";
        String format = "png";
        HashMap<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 条形码的格式是 BarcodeFormat.EAN_13
        // 二维码的格式是BarcodeFormat.QR_CODE
        BitMatrix bm = new MultiFormatWriter().encode(text,
                BarcodeFormat.EAN_13, width, height, hints);

        File out = new File("new.png");
        // 生成条形码图片
        // File out = new File("ean3.png");

        writeBitMatricToFile(bm, format, out);
    }
}
