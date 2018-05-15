package com.alvin.smilesb101.brieftalk.Bean;

import com.alvin.smilesb101.brieftalk.Bean.ShowApiListBean;
import com.alvin.smilesb101.brieftalk.Bean.ShowBaiSiBuDeBean;

import java.io.Serializable;

public class ShowApiBSBDBean implements Serializable {
    int ret_code;
    ShowApiListBean<ShowBaiSiBuDeBean> pagebean;

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public ShowApiListBean<ShowBaiSiBuDeBean> getPagebean() {
        return pagebean;
    }

    public void setPagebean(ShowApiListBean<ShowBaiSiBuDeBean> pagebean) {
        this.pagebean = pagebean;
    }
}
