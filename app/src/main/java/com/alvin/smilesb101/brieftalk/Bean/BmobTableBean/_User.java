package com.alvin.smilesb101.brieftalk.Bean.BmobTableBean;

import java.io.Serializable;
import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class _User extends BmobUser implements Serializable{
    public String userHeader;
    public String openid;
    public String userId;
    public String carrer;
    public String education;
    public String gender;
    public boolean qqSign;
    public String qqUserName;
    public String region;
    public String school;
    public String simpleDes;
    public Date birthday;
}
