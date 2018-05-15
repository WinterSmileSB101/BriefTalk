package com.alvin.smilesb101.brieftalk.View.Interface.Fragment;

import android.os.Handler;

import com.alvin.smilesb101.brieftalk.Bean.BaiDuTranslateBean;
import com.alvin.smilesb101.brieftalk.Bean.YouDaoTranslateDataBean;


public interface ITranslateFrament {
    void showLoading();
    void closeLoading();
    void showError(String error);
    void showTranslateResult(BaiDuTranslateBean translateBean);

    void showYouDaoTranslateOnline(YouDaoTranslateDataBean translateDataBean);

    Handler getHandler();
}
