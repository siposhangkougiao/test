package com.mtnz.util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.omg.CORBA.StringHolder;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

/*
    Created by xxj on 2018\3\27 0027.  
 */
public class WPush {



    public static String wpush(String openid, String token){
        JSONObject json=packJsonmsg("11","11","11","11","111");
        String success=remind(openid,"VBqGiDawTbZ13AHWXBICDwBAcr92S-TjYtbHmZp_dOQ","","#173177",json,token);
        return success;
    }






    public static JSONObject packJsonmsg(String first, String keyword1, String keyword2,String keyword3,String remark){
        JSONObject json = new JSONObject();
        try {
            JSONObject jsonFirst = new JSONObject();
            jsonFirst.put("value", first);
            jsonFirst.put("color", "#173177");
            json.put("first", jsonFirst);


            JSONObject jsonOrderMoneySum = new JSONObject();
            jsonOrderMoneySum.put("value", keyword1);
            jsonOrderMoneySum.put("color", "#173177");
            json.put("keyword1", jsonOrderMoneySum);


            JSONObject jsonOrderProductName = new JSONObject();
            jsonOrderProductName.put("value", keyword2);
            jsonOrderProductName.put("color", "#173177");
            json.put("keyword2", jsonOrderProductName);


            JSONObject jsonOrderProductName1 = new JSONObject();
            jsonOrderProductName1.put("value", keyword3);
            jsonOrderProductName1.put("color", "#173177");
            json.put("keyword3", jsonOrderProductName1);


            JSONObject jsonRemark = new JSONObject();
            jsonRemark.put("value", remark);
            jsonRemark.put("color", "#173177");
            json.put("remark", jsonRemark);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    public static String remind(String touser, String templat_id, String clickurl, String topcolor, JSONObject data,String token){
        System.out.println("==============发送通知=========================");
        String tmpurl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        System.err.println(token);
        String url = tmpurl.replace("ACCESS_TOKEN", token);
        JSONObject json = new JSONObject();
        try {
            json.put("touser", touser);
            json.put("template_id", templat_id);
            json.put("url", clickurl);
            json.put("topcolor", topcolor);
            json.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String result =httpsRequest(url, "POST", json.toString());
        try {
            JSONObject resultJson = JSONObject.fromObject(result);
            //JSONObject resultJson = new net.sf.json.JSONObject(result);
            System.err.println(resultJson.toString()+"返回值==================================");
            String errmsg = (String) resultJson.get("errmsg");
            System.out.println(errmsg+"==============发送通知=========================");
            if(!"ok".equals(errmsg)){  //如果为errmsg为ok，则代表发送成功，公众号推送信息给用户了。
                System.err.println("error==============发送失败=========================");
                return "error";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr){
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            System.out.println("连接超时：{}");
        } catch (Exception e) {
            System.out.println("https请求异常：{}");
        }
        return null;
    }
}
