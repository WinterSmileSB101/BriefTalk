package com.alvin.smilesb101.brieftalk.View.Interface.Activity;

import com.alvin.smilesb101.brieftalk.Bean.BingTodayBean;

public interface IWelcomeView {
    void ShowTodayBing(BingTodayBean bingToday);
    void onError(String error);
}
