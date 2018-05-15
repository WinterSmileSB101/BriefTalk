package com.alvin.smilesb101.brieftalk.Model;

import android.provider.SyncStateContract;
import android.util.Log;

import com.alvin.smilesb101.brieftalk.Bean.YouDaoTranslateDataBean;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.chdict.ChDictTranslate;
import com.youdao.sdk.chdict.ChDictor;
import com.youdao.sdk.chdict.DictListener;
import com.youdao.sdk.common.Constants;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class YouDaoTranslateModel {
    public final static YouDaoTranslateModel YOU_DAO_TRANSLATE_MODEL = new YouDaoTranslateModel();

    private final String TAG = "YouDaoTranslateModel";

    public interface TranslateWordOfflineListener{
        void onSuccess(List<ChDictTranslate> translates);
        void onError(String error,String error1);
    }

    private ChDictor dictor;
    public void translateWordOffline(String word, final TranslateWordOfflineListener listener){
        if(dictor==null){
            dictor = new ChDictor("dict", true);//放在assets/dict下
        }
        dictor.init();
        dictor.lookup(word, new DictListener() {
            @Override
            public void onResult(List<ChDictTranslate> list) {
                listener.onSuccess(list);
            }

            @Override
            public void onError(String s, String s1) {
                listener.onError(s,s1);
            }
        });
    }


    private Translator translator;

    public interface translateWordOnlineListener{
        void onSuccess(Translate result,String input,String requestId);
        void onListBack(List<Translate> list, List<String> list1, List<TranslateErrorCode> list2, String s);
        void onError(String Error);
    }

    public void translateWordOnline(String from, String to, String input, String appName, final translateWordOnlineListener listener){
        Language langFrom = LanguageUtils.getLangByName(from);
        Language langTo = LanguageUtils.getLangByName(to);

        TranslateParameters tps = new TranslateParameters.Builder()
                .source(appName)
                .from(langFrom)
                .to(langTo)
                .sound(Constants.SOUND_OUTPUT_MP3)
                .voice(Constants.VOICE_BOY_UK)
                .timeout(3000)
                .build();

        final long start = System.currentTimeMillis();

        translator = Translator.getInstance(tps);

        translator.lookup(input, "requestId", new TranslateListener() {
            @Override
            public void onError(TranslateErrorCode translateErrorCode, String s) {
                Log.i(TAG, "onError: "+translateErrorCode.toString());
                listener.onError("在查询的途中遇到不测："+translateErrorCode.name());
            }

            @Override
            public void onResult(Translate translate, String s, String s1) {
                listener.onSuccess(translate,s,s1);
            }

            @Override
            public void onResult(List<Translate> list, List<String> list1, List<TranslateErrorCode> list2, String s) {
                listener.onListBack(list,list1,list2,s);
            }
        });
    }
}
