package com.alvin.smilesb101.brieftalk.Presenter;

import android.util.Log;

import com.alvin.smilesb101.brieftalk.Bean.BingTodayBean;
import com.alvin.smilesb101.brieftalk.Model.BingTodayModel;
import com.alvin.smilesb101.brieftalk.View.Interface.Activity.IWelcomeView;

public class BingTodayPresenter {
    IWelcomeView view;
    String TAG = BingTodayPresenter.class.getSimpleName();

    public BingTodayPresenter(IWelcomeView view)
    {
        this.view = view;
    }

    public void getBingToday(){

        BingTodayModel.BING_TODAY_MODEL.getBingToday(new BingTodayModel.BingTodayModelListener() {
            @Override
            public void onSuccess(BingTodayBean todayBean) {
                Log.i(TAG, "onSuccess: 必应今日"+todayBean.getImg_1920());
                view.ShowTodayBing(todayBean);
            }

            @Override
            public void onError(String error) {
                Log.i(TAG, "onSuccess: 必应今日错误"+error);
                view.onError(error);
            }
        });
    }
}
