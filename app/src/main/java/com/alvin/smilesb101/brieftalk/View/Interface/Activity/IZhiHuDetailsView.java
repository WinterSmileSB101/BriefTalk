package com.alvin.smilesb101.brieftalk.View.Interface.Activity;

import com.alvin.smilesb101.brieftalk.Bean.ZhiHuContentInsideBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuContentOutSideBean;

public interface IZhiHuDetailsView {
    String getId();
    void showContent(ZhiHuContentInsideBean insideBean);
    void showContent(ZhiHuContentOutSideBean outSideBean);
    void onError(String error);
}
