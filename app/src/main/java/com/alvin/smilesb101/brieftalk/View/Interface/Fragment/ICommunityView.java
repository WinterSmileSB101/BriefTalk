package com.alvin.smilesb101.brieftalk.View.Interface.Fragment;

import com.alvin.smilesb101.brieftalk.Bean.BaiSiBuDeJieCommentBean;
import com.alvin.smilesb101.brieftalk.Bean.ShowBaiSiBuDeBean;

import java.util.ArrayList;

public interface ICommunityView {
    void onSuccess(ArrayList<ShowBaiSiBuDeBean> bsbdBeans);
    void onError(String error);
}
