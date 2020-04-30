package com.mtnz.util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

/**
* @Description:    七牛云工具
* @Author:         ZPX
* @CreateDate:     2019/11/28 10:24
* @Version:        1.0
*/
public class QiniuUtils {
    // 设置需要操作的账号的AK和SK
    private static final String ACCESS_KEY = "spnYj8X7EINqVRkTNEKfh197BHlQ1MMHhUb15y6i";
    private static final String SECRET_KEY = "zIP6bdZFAuuag_7yYB8X0VeUFT8gLhAbRhGqHz2B";

    // 要上传的空间
    private static final String bucketname = "xikaidan";

    // 密钥
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    //上传文件的路径
    private static final String FilePath = "C:\\Users\\Dell\\Desktop\\动态壁纸\\";  //本地要上传文件路径

    //返回token
    public static String getUpToken() {
        return auth.uploadToken(bucketname, null, 3600, new StringMap().put("insertOnly", 1));
    }

    /**
     *
     * @param headimgurl 需要下载图片的URL
     * @param FilePath 上传文件的路径
     * @throws IOException
     */
    //下载图片
    public static  void download(String headimgurl,String FilePath) throws IOException {
        URL url1 = new URL(headimgurl);
        URLConnection uc = url1.openConnection();
        InputStream inputStream = uc.getInputStream();
        FileOutputStream out = new FileOutputStream(FilePath);
        int j = 0;
        while ((j = inputStream.read()) != -1) {
            out.write(j);
        }
        inputStream.close();
    }




    /**
     * @param FilePath 上传文件的路径
     * @param key //上传到七牛后保存的文件名
     * @throws Exception
     */
    //普通上传
    public static void upload(String key,String FilePath) throws Exception{
        try {

            //构造一个带指定Zone对象的配置类
            Configuration cfg = new Configuration(Zone.zone1());

            UploadManager uploadManager = new UploadManager(cfg);

            //调用put方法上传
            Response res = uploadManager.put(FilePath, key, getUpToken());
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
    }


    /**
     * 公有空间返回文件URL
     * @param fileName 文件名称
     * @param domainOfBucket
     * @return
     */
    public static String publicFile(String fileName,String domainOfBucket) {
        /*String encodedFileName=null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String finalUrl = String.format("%s/%s", domainOfBucket,encodedFileName );*/
        String finalUrl = domainOfBucket + fileName;
        //System.out.println(finalUrl);
        return finalUrl;
    }

    public static void main(String[] args) throws Exception {
        //publicFile("abc.jpg","C:\\Users\\Dell\\Desktop\\工作\\uploadImgs\\20180521\\d7f9722f56c64f43acfffeee1c726f49.jpg");
        //upload("xkd/2020048/abc.jpg","C:\\Users\\Dell\\Desktop\\工作\\uploadImgs\\20180521\\d7f9722f56c64f43acfffeee1c726f49.jpg");
        //System.out.println(publicFile("xkd/2020048/abc.jpg","http://img.nongshoping.com/"));
        /*ArrayList<String> listFilePath = new ArrayList<String>();
        ArrayList<String> listFileName = new ArrayList<String>();
        getAllFileName("C:\\Users\\Dell\\Desktop\\uploadImgs\\",listFilePath,listFileName);
        for (int i = 0; i < listFilePath.size(); i++) {
            String name = listFilePath.get(i);
            if(name.contains(".jpg")){
                //upload("xkd/2020048/abc.jpg","C:\\Users\\Dell\\Desktop\\工作\\uploadImgs\\20180521\\d7f9722f56c64f43acfffeee1c726f49.jpg");
                String aa = "xkd" + listFileName.get(i);
                upload(aa,name);
                //System.out.println(name);
                //System.out.println(aa);
            }
        }
        System.out.println(listFilePath.size());
        System.out.println(listFileName.size());*/
    }

    public static void getAllFileName(String path, ArrayList<String> listFilePath,ArrayList<String> listFileName){
        File file = new File(path);
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null){
            String [] completNames = new String[names.length];
            for(int i=0;i<names.length;i++){
                completNames[i]=(path+names[i]).replaceAll("\\\\","/");
                String aa = path+names[i];
                String [] bb = aa.split("\\\\");
                String cc = "/"+bb[bb.length-2]+"/"+bb[bb.length-1];
                //cc = cc.replaceAll("\\\\","/");
                listFileName.add(cc);
            }
            listFilePath.addAll(Arrays.asList(completNames));
        }
        for(File a:files){
            if(a.isDirectory()){
                //如果文件夹下有子文件夹，获取子文件夹下的所有文件全路径。
                getAllFileName(a.getAbsolutePath()+"\\",listFilePath,listFileName);
            }
        }
    }

}
