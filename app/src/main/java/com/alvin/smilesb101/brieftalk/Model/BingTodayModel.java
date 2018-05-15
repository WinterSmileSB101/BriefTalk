package com.alvin.smilesb101.brieftalk.Model;

import com.alvin.smilesb101.brieftalk.Bean.BingTodayBean;
import com.alvin.smilesb101.brieftalk.Bean.ShowApiBingTodayBean;
import com.alvin.smilesb101.brieftalk.InterfaceAPI.BingService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BingTodayModel {
    public static final BingTodayModel BING_TODAY_MODEL = new BingTodayModel("http://route.showapi.com/");

    String TAG = BingTodayModel.class.getSimpleName();
    String baseUrl = "http://route.showapi.com/";
    Retrofit client;
    BingService service;

    public BingTodayModel(String baseUrl)
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
            service = client.create(BingService.class);
        }
    }

    public interface BingTodayModelListener{
        void onSuccess(BingTodayBean todayBean);
        void onError(String error);
    }

    public void getBingToday(final BingTodayModelListener listener)
    {
        Observable<ShowApiBingTodayBean> bingTodayCall = service.getBingToday(BingService.APP_ID,BingService.APP_SIGN);

        bingTodayCall.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ShowApiBingTodayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ShowApiBingTodayBean bean) {
                        if(bean.showapi_res_code == 0&&bean.showapi_res_body.ret_code == 0)
                        {
                            listener.onSuccess(bean.showapi_res_body.data);
                        }
                        else
                        {
                            listener.onError(bean.showapi_res_error+"\n"+bean.showapi_res_body.ret_message);
                        }

                    }
                });
    }
}
