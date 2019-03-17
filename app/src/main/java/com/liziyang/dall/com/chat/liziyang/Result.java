package com.liziyang.dall.com.chat.liziyang;

public class Result {
    //这里需要导入一个库implementation 'com.google.code.gson:gson:2.8.5'
    private int code;
    private String text;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
