package com.alvin.smilesb101.brieftalk.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Idata_BaiSiBuDeJie implements Serializable{
    String retcode;
    String appCode;
    String dataType;
    int pageToken;
    boolean hasNext;

    ArrayList<BaiSiBuDeJieCommentBean> data;

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getPageToken() {
        return pageToken;
    }

    public void setPageToken(int pageToken) {
        this.pageToken = pageToken;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public ArrayList<BaiSiBuDeJieCommentBean> getData() {
        return data;
    }

    public void setData(ArrayList<BaiSiBuDeJieCommentBean> data) {
        this.data = data;
    }
}
