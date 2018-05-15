package com.alvin.smilesb101.brieftalk.Presenter;

import com.alvin.smilesb101.brieftalk.Bean.ZhiHuContentInsideBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuContentOutSideBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuLastNewsBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuNewsExtraBean;
import com.alvin.smilesb101.brieftalk.Model.ZhiHuNewsModel;
import com.alvin.smilesb101.brieftalk.View.Interface.Activity.IZhiHuDetailsView;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.IDictionaryFragment;

public class ZhiHuNewsPresenter {
    private final String TAG = "ZhiHuNewsPresenter";

    private IDictionaryFragment view;
    private IZhiHuDetailsView detailsView;

    public ZhiHuNewsPresenter(IDictionaryFragment view){
        this.view = view;
    }

    public ZhiHuNewsPresenter(IZhiHuDetailsView view){
        this.detailsView = view;
    }

    public void getLastNews(){
        ZhiHuNewsModel.ZHI_HU_NEWS_MODEL.getLastNews(new ZhiHuNewsModel.LastNewsListener() {
            @Override
            public void onSuccess(ZhiHuLastNewsBean lastNewsBean) {
                view.showHotList(lastNewsBean);
            }

            @Override
            public void onError(String error) {
                view.onError(error);
            }
        });
    }

    public void getNewsExtra(final String id,final int pos){
        ZhiHuNewsModel.ZHI_HU_NEWS_MODEL.getNewsExtra(id,new ZhiHuNewsModel.NewsExtraListener() {
            @Override
            public void onSuccess(ZhiHuNewsExtraBean extraBean) {
                view.addNewsExtra(id,pos,extraBean);
            }

            @Override
            public void onError(String error) {
                view.onError(error);
            }
        });
    }


    public void getContentById(){
        ZhiHuNewsModel.ZHI_HU_NEWS_MODEL.getStoriesContentById(detailsView.getId(), new ZhiHuNewsModel.NewsContentListener() {
            @Override
            public void onSuccess(ZhiHuContentInsideBean insideBean) {
                if(insideBean!=null)
                {
                    detailsView.showContent(insideBean);
                }
            }

            @Override
            public void onSuccess(ZhiHuContentOutSideBean outSideBean) {
                if(outSideBean!=null)
                {
                    detailsView.showContent(outSideBean);
                }
            }

            @Override
            public void onError(String error) {
                detailsView.onError(error);
            }
        });
    }
}
