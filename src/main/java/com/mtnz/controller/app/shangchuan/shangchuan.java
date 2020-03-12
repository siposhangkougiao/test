package com.mtnz.controller.app.shangchuan;

import com.mtnz.util.Const;
import com.mtnz.util.DateUtil;
import com.mtnz.util.PathUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/*
    Created by xxj on 2018\8\13 0013.  
 */
@Controller
@RequestMapping(value = "app/shangchuan")
public class shangchuan {

    //@RequestMapping("/picture", produces = "text/html;charset=UTF-8")
    @RequestMapping(value = "picture", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String uploadPicture(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //PageData pd = new PageData();
        //pd = this.getPageData();
        //logBefore(logger, "小程序上传图片");
        String img="";
        String destPath="";
        String fileName="";
        String path = PathUtil.getClasspath() + Const.FILEPATHIMG + "pintu/" + DateUtil.getDays()+"/"; // 文件上传路径
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }

        request.setCharacterEncoding("utf-8"); // 设置编码
        // 获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // 如果没以下两行设置的话,上传大的文件会占用很多内存，
        // 设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同
        /**
         * 原理: 它是先存到暂时存储室，然后再真正写到对应目录的硬盘上， 按理来说当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
         * 然后再将其真正写到对应目录的硬盘上
         */
        factory.setRepository(dir);
        // 设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
        factory.setSizeThreshold(1024 * 1024);
        // 高水平的API文件上传处理
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> list = upload.parseRequest(request);
            // List<FileItem> fileItems = ((ServletFileUpload)
            // fileUpload).parseRequest(request);
            FileItem picture = null;
            for (FileItem item : list) {
                // 获取表单的属性名字
                String name = item.getFieldName();
                // 如果获取的表单信息是普通的 文本 信息
                if (item.isFormField()) {
                    // 获取用户具体输入的字符串
                    String value = item.getString();
                    request.setAttribute(name, value);
                    //logger.debug("name=" + name + ",value=" + value);
                } else {
                    picture = item;
                }
            }

            // 自定义上传图片的名字为userId.jpg
            fileName = request.getAttribute("id") + ".mp3";
            img = Const.SERVERPATH + Const.FILEPATHIMG + DateUtil.getDays()+"/"+ fileName;
            destPath = path + fileName;
            //logger.debug("destPath=" + destPath);

            // 真正写到磁盘上
            File file = new File(destPath);
            OutputStream out = new FileOutputStream(file);
            InputStream in = picture.getInputStream();
            int length = 0;
            byte[] buf = new byte[1024];
            // in.read(buf) 每次读到的数据存放在buf 数组中
            while ((length = in.read(buf)) != -1) {
                // 在buf数组中取出数据写到（输出流）磁盘上
                out.write(buf, 0, length);
            }
            in.close();
            out.close();
        } catch (FileUploadException e1) {
            //logger.error("", e1);
        } catch (Exception e) {
            //logger.error("", e);
        }

		/*PrintWriter printWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		HashMap<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		// printWriter.write(JSON.toJSONString(res));
		printWriter.flush();*/
        //CompressPicDemo compressPicDemo=new CompressPicDemo();
        //compressPicDemo.compressPic(path,destPath,request.getAttribute("id").toString(),request.getAttribute("id").toString(),100,100,true);
        //compressPicDemo.compressPic("D:\\", "D:\\20170518_173218.jpg", "20170518_173218.jpg", "r111.jpg", 500, 500, true);
        //compressPicDemo.compressPic(PathUtil.getClasspath() + Const.FILEPATHIMG + "pintu/" + DateUtil.getDays()+"/", PathUtil.getClasspath() + Const.FILEPATHIMG + "pintu/" + DateUtil.getDays()+"/"+fileName, fileName, "",2000,2000,true);
        /*compressPicDemo.compressPic(PathUtil.getClasspath() + Const.FILEPATHIMG + "pintu/" + DateUtil.getDays()+"/", PathUtil.getClasspath() + Const.FILEPATHIMG + "pintu/" + DateUtil.getDays()+"/pic15150597054112.jpg", "pic15150597054112.jpg", "pic15150597054112.jpg", 10000, 10000, true);*/
        return img;
    }
}
