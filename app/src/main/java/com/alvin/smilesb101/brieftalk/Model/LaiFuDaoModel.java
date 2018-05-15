package com.alvin.smilesb101.brieftalk.Model;

import com.alvin.smilesb101.brieftalk.Bean.LaiFuDaoJoke;
import com.alvin.smilesb101.brieftalk.Bean.laiFuDaoJokeImage;
import com.alvin.smilesb101.brieftalk.InterfaceAPI.LaiFuDaoService;
import com.alvin.smilesb101.brieftalk.InterfaceAPI.kinsoftService;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LaiFuDaoModel {
    private final String TAG = this.getClass().getSimpleName();
    private Retrofit client;
    private String baseUrl = "http://api.laifudao.com/";
    private LaiFuDaoService service;


    public final static LaiFuDaoModel LAI_FU_DAO_MODEL = new LaiFuDaoModel("http://api.laifudao.com/");

    public LaiFuDaoModel(String baseUrl){
        if(baseUrl!=null||baseUrl.trim()!="")
        {
            this.baseUrl = baseUrl;
        }

        client = new Retrofit.Builder()
                .baseUrl(this.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = client.create(LaiFuDaoService.class);

    }

    public interface laifuDaoListener{
        void onJokeSuccess(ArrayList<LaiFuDaoJoke> laiFuDaoJokes);
        void onJokeImageSuccess(ArrayList<laiFuDaoJokeImage> laiFuDaoJokeImages);
        void onError(String error);
    }


    public void getLaiFuDaoJoke(final laifuDaoListener listener)
    {
        Observable<ArrayList<LaiFuDaoJoke>> jokeCall = service.getLaiFuDaoJoke();
        jokeCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<LaiFuDaoJoke>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<LaiFuDaoJoke> laiFuDaoJokes) {
                        listener.onJokeSuccess(laiFuDaoJokes);
                    }
                });
    }

    public void getLaiFuDaoJokeImage(final laifuDaoListener listener)
    {
        Observable<ArrayList<laiFuDaoJokeImage>> jokeCall = service.getLaiFuDaoJokeImage();
        jokeCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<laiFuDaoJokeImage>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<laiFuDaoJokeImage> laiFuDaoJokes) {
                        listener.onJokeImageSuccess(laiFuDaoJokes);
                    }
                });
    }
}
