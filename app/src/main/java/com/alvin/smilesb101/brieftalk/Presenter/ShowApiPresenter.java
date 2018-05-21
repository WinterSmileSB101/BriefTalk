package com.alvin.smilesb101.brieftalk.Presenter;

import com.alvin.smilesb101.brieftalk.Bean.HistoryTodayBean;
import com.alvin.smilesb101.brieftalk.Bean.ShenHuiFuBean;
import com.alvin.smilesb101.brieftalk.Bean.ShowBaiSiBuDeBean;
import com.alvin.smilesb101.brieftalk.Model.ShowAPiModel;
import com.alvin.smilesb101.brieftalk.Model.YiYuanApiModel;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.ICommunityView;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.IShenHuiFuView;
import com.alvin.smilesb101.brieftalk.View.Interface.IHistoryTodayView;

import java.util.ArrayList;

public class ShowApiPresenter {
    private IShenHuiFuView shenHuiFuView;

    private ICommunityView communityView;

    private IHistoryTodayView historyTodayView;

    public ShowApiPresenter(ICommunityView communityView) {
        this.communityView = communityView;
    }

    public ShowApiPresenter(IShenHuiFuView shenHuiFuView) {
        this.shenHuiFuView = shenHuiFuView;
    }

    public ShowApiPresenter(IHistoryTodayView historyTodayView) {
        this.historyTodayView = historyTodayView;
    }

    public void getShenHuiFu(int num, int page){
        ShowAPiModel.SHOW_A_PI_MODEL.getShenHuiFu(num, page, new ShowAPiModel.onShenHuiFuListener() {
            @Override
            public void onSuccess(ArrayList<ShenHuiFuBean> beans) {
                shenHuiFuView.showShenHuiFu(beans);
            }

            @Override
            public void onError(String error) {
                shenHuiFuView.onError(error);
            }
        });
    }

    public void getBSBDJ(String type,String title,int page){
        YiYuanApiModel.SHOW_A_PI_MODEL.getBSBD(type,title,page,new YiYuanApiModel.onBaiSiBuDeJieListener(){
            @Override
            public void onSuccess(ArrayList<ShowBaiSiBuDeBean> buDeBean) {
                communityView.onSuccess(buDeBean);
            }

            @Override
            public void onError(String error) {
                communityView.onError(error);
            }
        });
    }

    public void getHistoryToday(String date){
        YiYuanApiModel.SHOW_A_PI_MODEL.getHistoryToday(date,new YiYuanApiModel.onHistoryTodayListener(){

            @Override
            public void onSuccess(ArrayList<HistoryTodayBean> beans) {
                historyTodayView.ShowHistoryToday(beans);
            }

            @Override
            public void onError(String error) {
                historyTodayView.onError(error);
            }
        });
    }


}
