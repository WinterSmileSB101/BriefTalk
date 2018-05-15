package com.alvin.smilesb101.brieftalk.InterfaceAPI;

import com.alvin.smilesb101.brieftalk.Bean.ZhiHuLastNewsBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuNewsExtraBean;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ZhiHuService {
    @GET("api/4/news/latest")
    Observable<ZhiHuLastNewsBean> getZhiHuLastNews();

    @GET("https://news-at.zhihu.com/api/4/story-extra/{id}")
    Observable<ZhiHuNewsExtraBean> getNewsExtraData(@Path("id") String id);

    /**
     * 内容API
     * http://news-at.zhihu.com/api/4/news/latest
     * 最新参数是 latest
     * 获取谋篇文章为文章id
     */
    @GET("api/4/news/{param}")
    Observable<ResponseBody> getNewsCall(@Path("param") String param);
}
