package com.alvin.smilesb101.brieftalk.Presenter;

import com.alvin.smilesb101.brieftalk.Bean.TodayWordBean;
import com.alvin.smilesb101.brieftalk.Model.KingSoftModel;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.IDictionaryFragment;
import com.alvin.smilesb101.brieftalk.View.Utils.Helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class KingSoftTodayWordPresenter {
    private IDictionaryFragment view;

    private final String TAG = "KingSoftTodayWordPresenter";


    public KingSoftTodayWordPresenter(IDictionaryFragment view){
        this.view = view;
    }
/*
    public void getKingSoftToday(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String curDate = simpleDateFormat.format(date);

        KingSoftModel.KING_SOFT_MODEL.getTodayWords(curDate,new KingSoftModel.TodayWordListener(){

                    @Override
                    public void onSuccess(ArrayList<TodayWordBean> wordBeans) {
                        //view.showBanner(wordBeans);
                    }

                    @Override
                    public void onError(String error) {
                        view.onError(error);
                    }
                }
        );
    }*/

    public void getBanner(){
        String lastDate = Helper.getCurrentDate(-1);

        KingSoftModel.KING_SOFT_MODEL.getBanners(lastDate,new KingSoftModel.BannerListener(){
                    @Override
                    public void onBannerSuccess(ArrayList<String> images,ArrayList<String> titles) {
                        view.showBanner(images,titles);
                    }

                    @Override
                    public void onError(String error) {
                        view.onError(error);
                    }
                }
        );
    }
}
