package com.alvin.smilesb101.brieftalk.Model;

import com.alvin.smilesb101.brieftalk.Bean.BaiSiBuDeJieCommentBean;
import com.alvin.smilesb101.brieftalk.Bean.Idata_BaiSiBuDeJie;
import com.alvin.smilesb101.brieftalk.Bean.ShowApiBingTodayBean;
import com.alvin.smilesb101.brieftalk.InterfaceAPI.BingService;
import com.alvin.smilesb101.brieftalk.InterfaceAPI.IdataApiService;

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

public class IDataApiModel {
    public static final IDataApiModel I_DATA_API_MODEL = new IDataApiModel("http://route.showapi.com/");

    String TAG = BingTodayModel.class.getSimpleName();
    String baseUrl = "http://route.showapi.com/";
    Retrofit client;
    IdataApiService service;

    public IDataApiModel(String baseUrl)
    {
        if(baseUrl!=null&&baseUrl.trim()!="")
        {
            this.baseUrl = baseUrl;
        }

        if(client==null){
            client = new Retrofit.Builder()
                    .baseUrl(this.baseUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = client.create(IdataApiService.class);
        }
    }

    public interface OnBaiSiBuDeJieCommentLisenter{
        void onSuccess(ArrayList<BaiSiBuDeJieCommentBean> baiSiBuDeJies);
        void onError(String error);
    }

    public void GetBaiSiBuDeJieComments(String articleId,final OnBaiSiBuDeJieCommentLisenter listener)
    {
        String appKey = IdataApiService.APP_KEY;
        Map<String,String> quires = new HashMap<>();
        quires.put("apiKey",appKey);
        if(articleId!=null&&!articleId.isEmpty())
        {
            quires.put("apiKey",appKey);
        }

        Observable<Idata_BaiSiBuDeJie> bingTodayCall = service.GetBaiSiBuDeJieComments(quires);

        bingTodayCall.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Idata_BaiSiBuDeJie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Idata_BaiSiBuDeJie bean) {
                        if(bean.getRetcode() == "000000")
                        {
                            listener.onSuccess(bean.getData());
                        }
                        else
                        {
                            listener.onError("服务器被黑洞吃掉了=。=");
                        }

                    }
                });
    }
}
