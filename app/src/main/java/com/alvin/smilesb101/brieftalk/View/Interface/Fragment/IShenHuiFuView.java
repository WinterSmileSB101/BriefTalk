package com.alvin.smilesb101.brieftalk.View.Interface.Fragment;

import com.alvin.smilesb101.brieftalk.Bean.ShenHuiFuBean;

import java.util.ArrayList;

public interface IShenHuiFuView {
    void showShenHuiFu(ArrayList<ShenHuiFuBean> beans);
    void onError(String error);
}
