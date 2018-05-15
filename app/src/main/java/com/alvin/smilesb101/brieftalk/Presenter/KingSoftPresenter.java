package com.alvin.smilesb101.brieftalk.Presenter;

import android.util.Log;

import com.alvin.smilesb101.brieftalk.Bean.KingSoftWordBean;
import com.alvin.smilesb101.brieftalk.Model.KingSoftModel;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.IWordBookView;

public class KingSoftPresenter {
    static final String TAG = KingSoftPresenter.class.getSimpleName();

    IWordBookView bookView;

    public KingSoftPresenter(IWordBookView bookView) {
        this.bookView = bookView;
    }

    public void getTranslate(String word, final int pos){
        KingSoftModel.KING_SOFT_MODEL.getTranslate(word, new KingSoftModel.onTranslateListener() {
            @Override
            public void onSuccess(KingSoftWordBean bean) {
                Log.i(TAG, "onSuccess: "+bean);
                bookView.showWordTranslate(bean,pos);
            }

            @Override
            public void onError(String error) {
                bookView.onError(error);
            }
        });
    }
}
