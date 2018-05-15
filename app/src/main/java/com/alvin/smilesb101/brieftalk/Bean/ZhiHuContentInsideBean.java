package com.alvin.smilesb101.brieftalk.Bean;

import java.io.Serializable;

public class ZhiHuContentInsideBean implements Serializable {
    String body;
    String image_source;
    String title;
    String image;
    String share_url;
    String[] js;
    String ga_prefix;
    String type;
    String id;
    String[] css;
    String[] images;

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }

    public String getImage_source(){
        return image_source;
    }

    public void setImage_source(String image_source){
        this.image_source = image_source;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getShare_url(){
        return share_url;
    }

    public void setShare_url(String share_url){
        this.share_url = share_url;
    }

    public String[] getJs(){
        return js;
    }

    public void setJs(String[] js){
        this.js = js;
    }


    public String getGa_prefix(){
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix){
        this.ga_prefix = ga_prefix;
    }


    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String[] getCss(){
        return css;
    }

    public void setCss(String[] css){
        this.css = css;
    }

    public String[] getImages(){
        return images;
    }

    public void setImages(String[] images){
        this.images = images;
    }
}
