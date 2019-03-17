package com.liziyang.dall.com.chat.liziyang;

import java.lang.reflect.Type;
import java.util.Date;

public class ChatMessage {

    private String name;
    private String msg;
    private Type type;
    //类型发送消息还是接受消息
    private Date date;//时间
    //enum枚举
    public enum Type{
        INCOMING,OUTCOMING

    }
    public ChatMessage(){

    }

    public ChatMessage(String msg,Type type,Date date) {
        super();
        this.msg = msg;
        this.type = type;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
