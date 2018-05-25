package com.alvin.smilesb101.brieftalk.Presenter;

import com.alvin.smilesb101.brieftalk.Bean.BaiSiBuDeJieCommentBean;
import com.alvin.smilesb101.brieftalk.Bean.BaiSiBuDeUserInfoBean;
import com.alvin.smilesb101.brieftalk.Model.IDataApiModel;
import com.alvin.smilesb101.brieftalk.View.Interface.Activity.ICommentsView;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.ICommunityView;

import java.util.ArrayList;

public class IDataApiPresenter {
    ICommentsView commentsView;

    public IDataApiPresenter(ICommentsView commentsView) {
        this.commentsView = commentsView;
    }

    public void GetBaiSiDuDeJie(String articId){
        IDataApiModel.I_DATA_API_MODEL.GetBaiSiBuDeJieComments(articId, new IDataApiModel.OnBaiSiBuDeJieCommentLisenter() {
            @Override
            public void onSuccess(ArrayList<BaiSiBuDeJieCommentBean> baiSiBuDeJies) {
                commentsView.ShowComments(baiSiBuDeJies);
            }

            @Override
            public void onError(String error) {
                commentsView.onError(error);
            }
        });
    }

    public void GetBaiSiBuDeJieProfile(String userId){
        IDataApiModel.I_DATA_API_MODEL.GetBaiSiBuDeJieProfile(userId, new IDataApiModel.OnBaiSiBuDeJieProfileLisenter() {
            @Override
            public void onSuccess(ArrayList<BaiSiBuDeUserInfoBean> baiSiBuDeJies) {
                commentsView.ShowUsers(baiSiBuDeJies);
            }

            @Override
            public void onError(String error) {
                commentsView.onError(error);
            }
        });
    }
}
