package com.alvin.smilesb101.brieftalk.View.Interface;

import com.alvin.smilesb101.brieftalk.Bean.HistoryTodayBean;

import java.util.ArrayList;

public interface IHistoryTodayView {
    void ShowHistoryToday(ArrayList<HistoryTodayBean> bingToday);
    void onError(String error);
}
