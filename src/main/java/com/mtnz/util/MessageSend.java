package com.mtnz.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageSend {
    /**
     *
     * @param phone
     * @param msg
     */
    public static SendMessageResult sendMessage(String phone,String msg){
        SimpleDateFormat sf= new SimpleDateFormat("yyyyMMddHHmmss");
        String strsystime = sf.format(System.currentTimeMillis());//系统当前时间

        Map params = new HashMap();
        params.put("account", "MXT801044");
        params.put("ts", strsystime);
        params.put("pswd", MD5.md5("MXT801044" + "VKsJj*T886FJD" + strsystime));
        params.put("mobile", phone);
        params.put("msg", msg);
        params.put("needstatus", "true");
        params.put("resptype", "json");

        String param = "http://www.weiwebs.cn/msg/HttpBatchSendSM";
        String string = HttpUtils.post(param,params,3000,3000,"utf-8");
        System.out.println(">>>>>"+ string);
        return JSONObject.parseObject(string,SendMessageResult.class);
    }

    public static void main(String[] args) {
        /*SendMessageResult sendMessageResult=sendMessage("17638567709","【老王农资店】祝广大客户五一劳动节快乐！ 回T退订");
        System.out.println(sendMessageResult.getResult());*/
        /*System.out.println(getCount("【老王农资店】祝广大客户五一劳动节快乐！ 回T退订"));*/
        String aa = "【老王农资店】祝广大客户五一劳动节快乐！ 回T退订【老王农资店】祝广大客户五一劳动节快乐！ 回T退订";
        System.out.println(getCount(aa));
    }
    public static Integer getCount(String msg){
        String []aa = msg.split("");
        Integer bb = aa.length/70;
        Integer cc = aa.length%70;
        if(cc>0){
            bb = bb+1;
        }
        return bb;
    }

}
