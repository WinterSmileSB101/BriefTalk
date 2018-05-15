package com.alvin.smilesb101.brieftalk.InterfaceAPI;

import com.alvin.smilesb101.brieftalk.Bean.ShenHuiFuBean;
import com.alvin.smilesb101.brieftalk.Bean.ShowApiBean;
import com.alvin.smilesb101.brieftalk.Bean.ShowApiBSBDEntityBean;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ShowApiService {
    public static final String AppKey = "03020121d900f8d31700163c6a7b9371";

    //http://api.tianapi.com/txapi/

    /**
     *  获取神回复
     * @param queries page，key，num
     * @return
     */
    @GET("txapi/godreply/?key=APIKEY")
    Observable<ShowApiBean<ArrayList<ShenHuiFuBean>>> getShenHuiFu(@QueryMap Map<String,String> queries);



    @GET("255-1")
    Observable<ShowApiBSBDEntityBean> getBaiSiBuDeJie(@QueryMap Map<String,String> queries);
}
