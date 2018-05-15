package com.alvin.smilesb101.brieftalk.InterfaceAPI;

import com.alvin.smilesb101.brieftalk.Bean.KingSoftTranslateBean;
import com.alvin.smilesb101.brieftalk.Bean.TodayWordBean;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface kinsoftService {
    @GET("dsapi/")
    Observable<TodayWordBean> getTodayWords(@QueryMap Map<String,String> querys);


    @GET("index.php")
    Observable<KingSoftTranslateBean> getWordTranslate(@QueryMap Map<String,String> queries);
}
