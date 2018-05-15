package com.alvin.smilesb101.brieftalk.Model;

import com.alvin.smilesb101.brieftalk.Bean.ShenHuiFuBean;
import com.alvin.smilesb101.brieftalk.Bean.ShowApiBean;
import com.alvin.smilesb101.brieftalk.InterfaceAPI.LaiFuDaoService;
import com.alvin.smilesb101.brieftalk.InterfaceAPI.ShowApiService;

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

public class ShowAPiModel {
    public static final ShowAPiModel SHOW_A_PI_MODEL = new ShowAPiModel("http://api.tianapi.com/");
    private final String TAG = this.getClass().getSimpleName();
    private Retrofit client;
    private String baseUrl = "http://api.tianapi.com/";
    private ShowApiService service;


    public ShowAPiModel(String baseUrl){
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

    public interface onShenHuiFuListener{
        void onSuccess(ArrayList<ShenHuiFuBean> beans);
        void onError(String error);
    }

    public void getShenHuiFu(int num,int page,final onShenHuiFuListener listener){
        Map<String,String> queries = new HashMap<>();
        queries.put("key",service.AppKey);
        if(num>1)
        {
            queries.put("num",num+"");
        }

        if(page>1)
        {
            queries.put("page",page+"");
        }

        Observable<ShowApiBean<ArrayList<ShenHuiFuBean>>> shenhuifuCall = service.getShenHuiFu(queries);

        shenhuifuCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ShowApiBean<ArrayList<ShenHuiFuBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ShowApiBean<ArrayList<ShenHuiFuBean>> arrayListShowApiBean) {
                        listener.onSuccess(arrayListShowApiBean.newslist);
                    }
                });
    }
}
