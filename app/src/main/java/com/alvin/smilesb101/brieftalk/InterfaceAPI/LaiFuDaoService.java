package com.alvin.smilesb101.brieftalk.InterfaceAPI;

import com.alvin.smilesb101.brieftalk.Bean.LaiFuDaoJoke;
import com.alvin.smilesb101.brieftalk.Bean.laiFuDaoJokeImage;

import java.util.ArrayList;

import retrofit2.http.GET;
import rx.Observable;

public interface LaiFuDaoService {
    //http://api.laifudao.com/
    @GET("open/xiaohua.json")
    Observable<ArrayList<LaiFuDaoJoke>> getLaiFuDaoJoke();

    //http://api.laifudao.com/
    @GET("open/tupian.json")
    Observable<ArrayList<laiFuDaoJokeImage>> getLaiFuDaoJokeImage();
}
