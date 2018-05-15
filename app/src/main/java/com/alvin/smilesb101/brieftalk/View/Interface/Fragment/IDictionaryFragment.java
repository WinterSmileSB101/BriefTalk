package com.alvin.smilesb101.brieftalk.View.Interface.Fragment;

import com.alvin.smilesb101.brieftalk.Bean.TodayWordBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuLastNewsBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuNewsExtraBean;

import java.util.ArrayList;

public interface IDictionaryFragment {
    void showLoading();
    void closeLoading();
    void onError(String error);
    void showBanner(ArrayList<String> images,ArrayList<String> titles);

    void showHotList(ZhiHuLastNewsBean newsBean);
    void addNewsExtra(String id, int pos,ZhiHuNewsExtraBean extraBean);
}
