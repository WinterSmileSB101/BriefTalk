package com.alvin.smilesb101.brieftalk.View.Interface.Fragment;

import android.os.Handler;
import android.text.SpannableString;

import com.alvin.smilesb101.brieftalk.Bean.YouDaoTranslateDataBean;

import java.util.ArrayList;

public interface IOcrTranslateFragment {
    void showLoading();
    void closeLoading();
    void onError(String error);
    void showOcrTranslate(SpannableString spannableString, String input);
    void showOcrTranslateOnline(YouDaoTranslateDataBean translateDataBean,String input);

    Handler getHandler();
}
