package com.alvin.smilesb101.brieftalk.Model;

import android.util.Log;

import com.alvin.smilesb101.brieftalk.Bean.BaiDuTranslateBean;
import com.alvin.smilesb101.brieftalk.InterfaceAPI.BaiduService;
import com.alvin.smilesb101.brieftalk.View.Utils.Helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BaiDuTranslateModel {
    private final String TAG = "BaiDuTranslateModel";
    public final static BaiDuTranslateModel BAI_DU_TRANSLATE_MODEL = new BaiDuTranslateModel("http://api.fanyi.baidu.com/");

    private String baseUrl = "http://api.fanyi.baidu.com/";

    private String appId = "20180504000153246";
    private String salt;
    private String key = "s5vSAvyJUGc1p3sqJrbY";
    private Retrofit client;
    private BaiduService service;

    public BaiDuTranslateModel(String baseUrl){
        if(baseUrl!=null||baseUrl.trim()!="")
        {
            this.baseUrl = baseUrl;
        }

        client = new Retrofit.Builder()
                .baseUrl(this.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = client.create(BaiduService.class);

    }


    private String getSign(String transWord){
        String sign = null;
        try {
            salt = String.valueOf(System.currentTimeMillis());
            //salt = "1435660288";
            appId = URLEncoder.encode(appId,"utf-8");
            salt = URLEncoder.encode(salt,"utf-8");
            key = URLEncoder.encode(key,"utf-8");
            String signSrc = appId+transWord+salt+key;
            Log.i(TAG, "getSign: "+signSrc);
            sign = Helper.md5(signSrc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sign;
    }

    public interface TranslateListener{
        void onSuccess(BaiDuTranslateBean translateBean);
        void onError(String error);
    }

    public void translateWord(String word,final TranslateListener listener){
        translateWord("auto","en",word,listener);
    }

    public void translateWord(String from,String to,String word,final TranslateListener listener){
        String sign = getSign(word);
        Map<String,String> quries = new HashMap<>();
        quries.put("q",word);
        quries.put("from",from);
        quries.put("to",to);
        quries.put("appid",appId);
        quries.put("salt",salt+"");
        Log.i(TAG, "translateWord: "+sign);
        quries.put("sign",sign);

        Observable<ResponseBody> transCall = service.getTranslate(quries);

        transCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody translateBean) {
                        try {
                            Log.i(TAG, "onNext: "+translateBean.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //listener.onSuccess(translateBean);
                    }
                });

    }
}
