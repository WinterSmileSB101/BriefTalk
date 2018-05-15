package com.alvin.smilesb101.brieftalk.Bean;

import java.io.Serializable;

public class KingSoftTranslateBean implements Serializable {
    String errno;
    String errmsg;
    KingSoftWordBean baesInfo;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public KingSoftWordBean getBaseInfo() {
        return baesInfo;
    }

    public void setBaseInfo(KingSoftWordBean baseInfo) {
        this.baesInfo = baseInfo;
    }
}
