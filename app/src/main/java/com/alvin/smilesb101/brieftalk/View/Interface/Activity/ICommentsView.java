package com.alvin.smilesb101.brieftalk.View.Interface.Activity;

import com.alvin.smilesb101.brieftalk.Bean.BaiSiBuDeJieCommentBean;
import com.alvin.smilesb101.brieftalk.Bean.BaiSiBuDeUserInfoBean;

import java.util.ArrayList;

public interface ICommentsView {
    void ShowUsers(ArrayList<BaiSiBuDeUserInfoBean> bingToday);
    void ShowComments(ArrayList<BaiSiBuDeJieCommentBean> bingToday);
    void onError(String error);
}
