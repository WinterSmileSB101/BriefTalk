package com.alvin.smilesb101.brieftalk.Bean;

import java.io.Serializable;

public class SkinBean implements Serializable {
    String skin_name;
    String skin_img;
    int skin_color;
    String skin_path;
    boolean is_chose;

    public SkinBean(String skin_name,String skin_img,String skin_path,boolean is_chose){
        this.skin_name = skin_name;
        this.skin_img = skin_img;
        this.skin_path = skin_path;
        this.is_chose = is_chose;
    }

    public SkinBean(String skin_name,String skin_img,int skin_color,String skin_path,boolean is_chose){
        this.skin_name = skin_name;
        this.skin_img = skin_img;
        this.skin_color = skin_color;
        this.skin_path = skin_path;
        this.is_chose = is_chose;
    }

    public String getSkin_name(){
        return skin_name;
    }

    public void setSkin_name(String skin_name){
        this.skin_name = skin_name;
    }

    public String getSkin_path(){
        return skin_path;
    }

    public void setSkin_path(String skin_path){
        this.skin_path = skin_path;
    }

    public boolean is_chose(){
        return is_chose;
    }

    public void setIs_chose(boolean is_chose){
        this.is_chose = is_chose;
    }

    public String getSkin_img(){
        return skin_img;
    }

    public void setSkin_img(String skin_img){
        this.skin_img = skin_img;
    }

    public int getSkin_color(){
        return skin_color;
    }

    public void setSkin_color(int skin_color){
        this.skin_color = skin_color;
    }
}
