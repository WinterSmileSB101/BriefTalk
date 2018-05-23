package com.alvin.smilesb101.brieftalk.Presenter;

import com.alvin.smilesb101.brieftalk.Bean.BaiSiBuDeJieCommentBean;
import com.alvin.smilesb101.brieftalk.Model.IDataApiModel;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.ICommunityView;

import java.util.ArrayList;

public class IDataApiPresenter {
    ICommunityView communityView;

    public IDataApiPresenter(ICommunityView communityView) {
        this.communityView = communityView;
    }

    public void GetBaiSiDuDeJie(String articId){
        IDataApiModel.I_DATA_API_MODEL.GetBaiSiBuDeJieComments(articId, new IDataApiModel.OnBaiSiBuDeJieCommentLisenter() {
            @Override
            public void onSuccess(ArrayList<BaiSiBuDeJieCommentBean> baiSiBuDeJies) {
                communityView.onCommentsSuccess(baiSiBuDeJies);
            }

            @Override
            public void onError(String error) {
                communityView.onError(error);
            }
        });
    }
}
