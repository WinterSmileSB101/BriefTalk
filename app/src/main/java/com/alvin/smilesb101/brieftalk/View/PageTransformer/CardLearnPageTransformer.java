package com.alvin.smilesb101.brieftalk.View.PageTransformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

public class CardLearnPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        if(position<0)
        {
            ViewCompat.setTranslationY(page,20);

        }
    }
}
