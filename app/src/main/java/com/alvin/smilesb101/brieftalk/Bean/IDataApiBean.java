package com.alvin.smilesb101.brieftalk.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class IDataApiBean<T> implements Serializable {
    boolean hasNest;
    String retcode;
    String appCode;
    String dataType;
    String pageToken;
    ArrayList<T> data;

    public boolean isHasNest() {
        return hasNest;
    }

    public void setHasNest(boolean hasNest) {
        this.hasNest = hasNest;
    }

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

    public String getPageToken() {
        return pageToken;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}
