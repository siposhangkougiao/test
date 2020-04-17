package com.mtnz.util;

public class SendMessageResult {

    /**
     * 返回状态 0成功
     */
    private Integer result;

    private String msgid;

    private String ts;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
