package com.alvin.smilesb101.brieftalk.Presenter;

import com.alvin.smilesb101.brieftalk.Bean.SkinBean;
import com.alvin.smilesb101.brieftalk.Model.SkinModel;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.ISkinActivityView;

import java.util.ArrayList;

public class SkinActivityPresenter {
    ISkinActivityView iSkinActivityView;

    public SkinActivityPresenter(ISkinActivityView iSkinActivityView){
        this.iSkinActivityView = iSkinActivityView;
    }

    public void getSkinList()
    {
        SkinModel.SKIN_MODEL.getSkinList(new SkinModel.getSkinCallBack(){
            @Override
            public void onSucess(ArrayList<SkinBean> skinBeanArrayList){
                iSkinActivityView.onOfficalSkinBcak(skinBeanArrayList);
            }

            @Override
            public void onError(String error){
                iSkinActivityView.onError(error);
            }
        });
    }
}
