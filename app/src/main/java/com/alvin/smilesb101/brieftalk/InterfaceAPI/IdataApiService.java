package com.alvin.smilesb101.brieftalk.InterfaceAPI;

import com.alvin.smilesb101.brieftalk.Bean.BaiSiBuDeUserInfoBean;
import com.alvin.smilesb101.brieftalk.Bean.IDataApiBean;
import com.alvin.smilesb101.brieftalk.Bean.Idata_BaiSiBuDeJie;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface IdataApiService {
    public static final String APP_KEY = "b8wqMxQRU3OmAyDbUExR1RvQu58cSOI5AstK7rNj4Vj1jNEmzvHQpgkjTfBud9WQ";

    @GET("comment/baisibudejie")
    Observable<Idata_BaiSiBuDeJie> GetBaiSiBuDeJieComments(@QueryMap Map<String,String> queries);

    @GET("profile/baisibudejie")
    Observable<IDataApiBean<BaiSiBuDeUserInfoBean>> GetBaiSiBuDeJieProfile(@QueryMap Map<String,String> queries);
}
