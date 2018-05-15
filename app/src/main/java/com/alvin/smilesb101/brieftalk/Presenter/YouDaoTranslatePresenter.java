package com.alvin.smilesb101.brieftalk.Presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;

import com.alvin.smilesb101.brieftalk.Bean.YouDaoTranslateDataBean;
import com.alvin.smilesb101.brieftalk.Model.YouDaoTranslateModel;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.ITranslateFrament;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;

import java.util.List;

import rx.Subscriber;

public class YouDaoTranslatePresenter {
    private ITranslateFrament view;

    private final String TAG = "YouDaoTranslatePresenter";

    public YouDaoTranslatePresenter(ITranslateFrament view){
        this.view = view;
    }

    public void translateWordOffline(String word){

    }

    public void translateWordOnline(String langFrom,String langTo,String input,String appName){
        YouDaoTranslateModel.YOU_DAO_TRANSLATE_MODEL.translateWordOnline(langFrom,langTo,input,appName, new YouDaoTranslateModel.translateWordOnlineListener() {
            @Override
            public void onSuccess(final Translate result, String input, String requestId) {
                Handler handler = view.getHandler();
                handler.post(new Runnable() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void run() {
                        YouDaoTranslateDataBean translateDataBean = new YouDaoTranslateDataBean(System.currentTimeMillis(),result);
                        Log.i(TAG, "run: "+translateDataBean.getTranslate().getTranslations().get(0));
                        view.showYouDaoTranslateOnline(translateDataBean);
                    }
                });
            }

            @Override
            public void onListBack(List<Translate> list, List<String> list1, List<TranslateErrorCode> list2, String s) {

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onError(String Error) {
                Log.i(TAG, "onError: "+Error);
            }
        });
    }
}
