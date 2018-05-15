package com.alvin.smilesb101.brieftalk.Presenter;

import android.util.Log;

import com.alvin.smilesb101.brieftalk.Bean.BaiDuTranslateBean;
import com.alvin.smilesb101.brieftalk.Model.BaiDuTranslateModel;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.ITranslateFrament;
import com.alvin.smilesb101.brieftalk.View.Utils.Helper;

public class BaiduTranslatePresenter {
    private ITranslateFrament view;

    private final String TAG = "BaiduTranslatePresenter";

    public BaiduTranslatePresenter(ITranslateFrament view){
        this.view = view;
    }

    public void translateWord(String word){
        if(Helper.isChinese(word))
        {
            translateWord("auto","en",word);
        }
        else{
            translateWord("auto","zh",word);
        }
    }

    public void translateWord(String from,String to,String word){

        BaiDuTranslateModel.BAI_DU_TRANSLATE_MODEL.translateWord(from, to, word, new BaiDuTranslateModel.TranslateListener() {
            @Override
            public void onSuccess(BaiDuTranslateBean translateBean) {
                view.showTranslateResult(translateBean);
            }

            @Override
            public void onError(String error) {
                Log.i(TAG, "onError: "+error);
                view.showError(error);
            }
        });
    }
}
