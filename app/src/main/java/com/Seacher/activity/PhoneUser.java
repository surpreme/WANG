package com.Seacher.activity;




public class PhoneUser {
    //声明三个指数 方便主界面java调用
    private int _id;//手机联系人用户id
    private String phone;//手机联系人号码
    private String displayName;//手机联系人名字

    //这三个都生成get与set函数 不用任何处理

    public int get_id() {
        return _id;
    }


    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public PhoneUser(String displayName) {
        this.displayName = displayName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public PhoneUser(int _id) {
        this._id = _id;
    }
    //构造函数
    public PhoneUser(int _id,String displayName,String phone){
        super();
        this._id=_id;
        this.displayName=displayName;
        this.phone=phone;

    }
    //默认构造 辅助完成主界面的初始化工作
    public  PhoneUser(){
        super();
    }
    //生成getString 方便日志属性的输入打印

    @Override
    public String toString() {
        return "PhoneUser{" + "_id=" + _id + ", displayName='" + displayName + '\'' + ", phone='" + phone + '\'' + '}';
    }
}
