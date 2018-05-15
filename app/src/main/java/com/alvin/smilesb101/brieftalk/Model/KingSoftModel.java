package com.alvin.smilesb101.brieftalk.Model;

import android.util.Log;

import com.alvin.smilesb101.brieftalk.Bean.KingSoftTranslateBean;
import com.alvin.smilesb101.brieftalk.Bean.KingSoftWordBean;
import com.alvin.smilesb101.brieftalk.Bean.TodayWordBean;
import com.alvin.smilesb101.brieftalk.InterfaceAPI.kinsoftService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

public class KingSoftModel {
    private final String TAG = "KingSoftModel";
    private Retrofit client;
    private kinsoftService service;
    private String baseUrl = "http://open.iciba.com/";

    public final static KingSoftModel KING_SOFT_MODEL = new KingSoftModel("http://open.iciba.com/");

    public interface TodayWordListener{
        void onSuccess(ArrayList<TodayWordBean> wordBeans);
        void onError(String error);
    }

    public interface BannerListener{
        void onBannerSuccess(ArrayList<String> images,ArrayList<String> titles);
        void onError(String error);
    }

    public KingSoftModel(String baseUrl){
        if(baseUrl!=null||baseUrl.trim()!="")
        {
            this.baseUrl = baseUrl;
        }

        client = new Retrofit.Builder()
                    .baseUrl(this.baseUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        service = client.create(kinsoftService.class);

    }

    /*public void getTodayWords(String date,final TodayWordListener wordListener){
        Map<String,String> querys = new HashMap<>();
        querys.put("date",date);
        Observable<TodayWordBean> curCall = service.getTodayWords(querys);

        querys = new HashMap<>();
        querys.put("date",date);
        querys.put("type", "last");
        Observable<TodayWordBean> lastCall = service.getTodayWords(querys);

        querys = new HashMap<>();
        querys.put("date",date);
        querys.put("type", "next");
        Observable<TodayWordBean> nextCall = service.getTodayWords(querys);

        Observable.zip(lastCall, curCall, nextCall, new Func3<TodayWordBean, TodayWordBean, TodayWordBean, ArrayList<TodayWordBean>>() {
            @Override
            public ArrayList<TodayWordBean> call(TodayWordBean wordBean, TodayWordBean wordBean2, TodayWordBean wordBean3) {
                ArrayList<TodayWordBean> wordBeans = new ArrayList<>();
                wordBeans.add(wordBean);
                wordBeans.add(wordBean2);
                wordBeans.add(wordBean3);
                return wordBeans;
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<TodayWordBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        wordListener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<TodayWordBean> todayWordBeans) {
                        wordListener.onSuccess(todayWordBeans);
                    }
                });
    }*/

    public void getBanners(String date,final BannerListener bannerListener){
        Map<String,String> querys = new HashMap<>();
        querys.put("date",date);
        Observable<TodayWordBean> curCall = service.getTodayWords(querys);

        querys = new HashMap<>();
        querys.put("date",date);
        querys.put("type", "last");
        Observable<TodayWordBean> lastCall = service.getTodayWords(querys);

        querys = new HashMap<>();
        querys.put("date",date);
        querys.put("type", "next");
        Observable<TodayWordBean> nextCall = service.getTodayWords(querys);

        /*
        Log.i(TAG, "getBanners: 获取banner"+curCall);
        nextCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TodayWordBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: 错误"+e);

            }

            @Override
            public void onNext(TodayWordBean wordBean) {
                try {
                    Log.i(TAG, "onNext: "+wordBean.note);

                    //TodayWordBean wordBean1 = gson.fromJson(wordBean.string(),new TypeToken<TodayWordBean>(){}.getType());
                    //Log.i(TAG, "onNext: "+wordBean1.note);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

        Observable.zip(lastCall, curCall, nextCall, new Func3<TodayWordBean, TodayWordBean, TodayWordBean, ArrayList<TodayWordBean>>() {
            @Override
            public ArrayList<TodayWordBean> call(TodayWordBean wordBean, TodayWordBean wordBean2, TodayWordBean wordBean3) {
                ArrayList<TodayWordBean> wordBeans = new ArrayList<>();
                wordBeans.add(wordBean);
                wordBeans.add(wordBean2);
                wordBeans.add(wordBean3);
                return wordBeans;
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<TodayWordBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.i(TAG, "onError: 遇到错误");
                        bannerListener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<TodayWordBean> todayWordBeans) {
                        try {
                            //Log.i(TAG, "onNext: "+todayWordBeans.get(0));
                            ArrayList<String> images = new ArrayList<>();
                            ArrayList<String> titles = new ArrayList<>();
                            for (TodayWordBean bean : todayWordBeans) {
                                images.add(bean.picture2);
                                titles.add(bean.content);
                            }
                            bannerListener.onBannerSuccess(images, titles);
                        }
                        catch (Exception ex){
                            bannerListener.onError(ex.getMessage());
                        }
                    }
                });
    }


    public interface onTranslateListener{
        void onSuccess(KingSoftWordBean bean);
        void onError(String error);
    }

    private kinsoftService getKingSoftUnOfficalService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.iciba.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(kinsoftService.class);
    }

    public void getTranslate(String word,final onTranslateListener listener){
        Map<String,String> queries = new HashMap<>();
        queries.put("a","getWordMean");
        queries.put("c","search");
        queries.put("list","1");
        queries.put("word",word);

        kinsoftService service = getKingSoftUnOfficalService();

        Observable<KingSoftTranslateBean> wordCall = service.getWordTranslate(queries);

        wordCall.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<KingSoftTranslateBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: 翻译错误"+e.getMessage());
                    }

                    @Override
                    public void onNext(KingSoftTranslateBean kingSoftTranslateBean) {
                        Log.i(TAG, "onNext: 翻译成功"+kingSoftTranslateBean.getErrmsg());
                        listener.onSuccess(kingSoftTranslateBean.getBaseInfo());
                    }
                });
    }
}
