package com.alvin.smilesb101.brieftalk.InterfaceAPI;

import com.alvin.smilesb101.brieftalk.Bean.BaiDuTranslateBean;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface BaiduService {
    @GET("api/trans/vip/translate")
    Observable<ResponseBody> getTranslate(@QueryMap Map<String,String> queries);
}
