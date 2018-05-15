package com.alvin.smilesb101.brieftalk.View.Interface.Fragment;

import com.alvin.smilesb101.brieftalk.Bean.SkinBean;

import java.util.ArrayList;

public interface ISkinActivityView {
    void onOfficalSkinBcak(ArrayList<SkinBean> officalSkin);
    void onError(String error);
}
