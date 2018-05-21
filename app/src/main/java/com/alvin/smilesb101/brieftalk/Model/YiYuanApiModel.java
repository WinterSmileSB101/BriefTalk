package com.alvin.smilesb101.brieftalk.Model;

import android.util.Log;

import com.alvin.smilesb101.brieftalk.Bean.HistoryTodayBean;
import com.alvin.smilesb101.brieftalk.Bean.ShowApiBean;
import com.alvin.smilesb101.brieftalk.Bean.ShowApiCommonBean;
import com.alvin.smilesb101.brieftalk.Bean.ShowBaiSiBuDeBean;
import com.alvin.smilesb101.brieftalk.InterfaceAPI.ShowApiService;
import com.alvin.smilesb101.brieftalk.Bean.ShowApiBSBDEntityBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class YiYuanApiModel {
    public static final YiYuanApiModel SHOW_A_PI_MODEL = new YiYuanApiModel("http://route.showapi.com/");
    private final String TAG = this.getClass().getSimpleName();
    private Retrofit client;
    private String baseUrl = "http://route.showapi.com/";
    private ShowApiService service;

    public YiYuanApiModel(String baseUrl)
    {
        if(baseUrl!=null||baseUrl.trim()!="")
        {
            this.baseUrl = baseUrl;
        }

        client = new Retrofit.Builder()
                .baseUrl(this.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = client.create(ShowApiService.class);
    }

    static final String  appId = "63914";
    static final String appKey = "9ebd4d752fe94978b7cee5ce13f28e56";

    public interface onBaiSiBuDeJieListener{
        void onSuccess(ArrayList<ShowBaiSiBuDeBean> buDeBean);
        void onError(String error);
    }

    public void getBSBD(String type,String title,int page,final onBaiSiBuDeJieListener listener){
        Map<String,String> queries = new HashMap<>();
        queries.put("showapi_appid",appId);
        queries.put("showapi_sign",appKey);
        if(type!=null&&type.trim()!="")
        {
            queries.put("type",type);
        }

        if(title!=null&&title.trim()!="")
        {
            queries.put("title",title);
        }

        if(page>1)
        {
            queries.put("page",page+"");
        }

        Observable<ShowApiBSBDEntityBean> bsbdjCall = service.getBaiSiBuDeJie(queries);

        bsbdjCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ShowApiBSBDEntityBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ShowApiBSBDEntityBean showApiBSBDEntityBean) {
                        listener.onSuccess(showApiBSBDEntityBean.getShowapi_res_body().getPagebean().getContentlist());
                    }
                });

    }

    public interface onHistoryTodayListener{
        void onSuccess(ArrayList<HistoryTodayBean> beans);
        void onError(String error);
    }

    public void getHistoryToday(String date,final onHistoryTodayListener listener){
        Map<String,String> queries = new HashMap<>();
        queries.put("showapi_appid",appId);
        queries.put("showapi_sign",appKey);

        if(date!=null&&!date.isEmpty())
        {
            queries.put("date",date);
        }

        Observable<ShowApiCommonBean<HistoryTodayBean>> shenhuifuCall = service.getHistoryToday(queries);

        shenhuifuCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ShowApiCommonBean<HistoryTodayBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ShowApiCommonBean<HistoryTodayBean> arrayListShowApiBean) {
                        Log.i(TAG, "onNext: "+arrayListShowApiBean.getShowapi_res_code());
                        listener.onSuccess(arrayListShowApiBean.getShowapi_res_body().getList());
                    }
                });
    }
}
