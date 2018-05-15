package com.alvin.smilesb101.brieftalk.Model;


import android.util.Log;

import com.alvin.smilesb101.brieftalk.Bean.ZhiHuContentInsideBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuContentOutSideBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuLastNewsBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuNewsExtraBean;
import com.alvin.smilesb101.brieftalk.InterfaceAPI.ZhiHuService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ZhiHuNewsModel {
    private final String TAG = "ZhiHuNewsModel";
    private Retrofit client;
    private ZhiHuService service;
    private String baseUrl = "https://news-at.zhihu.com/";

    public final static ZhiHuNewsModel ZHI_HU_NEWS_MODEL = new ZhiHuNewsModel("https://news-at.zhihu.com/");

    public ZhiHuNewsModel(String baseUrl){
        if(baseUrl!=null||baseUrl.trim()!="")
        {
            this.baseUrl = baseUrl;
        }

        client = new Retrofit.Builder()
                .baseUrl(this.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = client.create(ZhiHuService.class);
    }

    public interface LastNewsListener{
        void onSuccess(ZhiHuLastNewsBean lastNewsBean);
        void onError(String error);
    }

    public void getLastNews(final LastNewsListener newsListener){
        Observable<ZhiHuLastNewsBean> lastNewsCall = service.getZhiHuLastNews();

        lastNewsCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhiHuLastNewsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        newsListener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhiHuLastNewsBean lastNewsBean) {
                        newsListener.onSuccess(lastNewsBean);
                    }
                });

    }


    public interface NewsExtraListener{
        void onSuccess(ZhiHuNewsExtraBean extraBean);
        void onError(String error);
    }

    public void getNewsExtra(String id,final NewsExtraListener extraListener){
        Observable<ZhiHuNewsExtraBean> extraBeanObservable = service.getNewsExtraData(id);

        extraBeanObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhiHuNewsExtraBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        extraListener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhiHuNewsExtraBean extraBean) {
                        extraListener.onSuccess(extraBean);
                    }
                });
    }


    public interface NewsContentListener{
        void onSuccess(ZhiHuContentInsideBean insideBean);
        void onSuccess(ZhiHuContentOutSideBean outSideBean);
        void onError(String error);
    }

    /**
     * 获取消息内容
     */
    public void getStoriesContentById(String id,final NewsContentListener linstener)
    {
        Observable<ResponseBody> contentCall = service.getNewsCall(id);
        contentCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        linstener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //成功
                        try {
                            Gson gson = new Gson();
                            JSONObject object = new JSONObject(responseBody.string());
                            if(object.getString("type").equals("0"))
                            {
                                //内部消息
                                Log.i(TAG,"onResponse: 内站消息： "+object.toString());
                                ZhiHuContentInsideBean insideBean = gson.fromJson(object.toString(),ZhiHuContentInsideBean.class);//获取对象
                                linstener.onSuccess(insideBean);//交给监听处理
                            }
                            else if(object.getString("type").equals("1"))
                            {
                                //外站消息
                                Log.i(TAG,"onResponse: 外站消息");
                                ZhiHuContentOutSideBean outSideBean = gson.fromJson(object.toString(),ZhiHuContentOutSideBean.class);//获取对象
                                linstener.onSuccess(outSideBean);//交给监听处理
                            }


                        } catch(JSONException e) {
                            e.printStackTrace();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
