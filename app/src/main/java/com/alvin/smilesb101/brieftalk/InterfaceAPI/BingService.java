package com.alvin.smilesb101.brieftalk.InterfaceAPI;

import com.alvin.smilesb101.brieftalk.Bean.BingTodayBean;
import com.alvin.smilesb101.brieftalk.Bean.ShowApiBingTodayBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface BingService {
    public static final String APP_ID = "63914";
    public static final String APP_SIGN = "9ebd4d752fe94978b7cee5ce13f28e56";

    /**
     * 获取今日的必应图片（一张）
     * http://route.showapi.com/1287-1
     * @param appId
     * @param appSign
     * @return
     */
    @GET("1287-1")
    Observable<ShowApiBingTodayBean> getBingToday(@Query("showapi_appid") String appId, @Query("showapi_sign") String appSign);
}
