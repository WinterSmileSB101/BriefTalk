package com.alvin.smilesb101.brieftalk.View.Interface.Fragment;

import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean.Word;
import com.alvin.smilesb101.brieftalk.Bean.KingSoftWordBean;

public interface IWordBookView {
    void showWordTranslate(KingSoftWordBean wordBean,int pos);
    void showWord(Word word);
    void onError(String error);
}
