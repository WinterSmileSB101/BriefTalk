package com.alvin.smilesb101.brieftalk.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean.Word;
import com.alvin.smilesb101.brieftalk.Bean.KingSoftWordBean;
import com.alvin.smilesb101.brieftalk.View.Adapter.Base.BaseFragmentPagerAdapter;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.View.Fragment.CardLearnFragment;

import java.util.ArrayList;

public class CardLearnPageAdapter extends BaseFragmentPagerAdapter {
    ArrayList<FragmentBase> cardLearnFragments;

    public CardLearnPageAdapter(FragmentManager fm, ArrayList<FragmentBase> fragmentBaseArrayList) {
        super(fm, fragmentBaseArrayList);
        cardLearnFragments = fragmentBaseArrayList;
        titles = new ArrayList<>();

        titleImages = new ArrayList<>();
        for (FragmentBase fragmentBase:fragmentBases) {
            titles.add(fragmentBase.getTitle());
            titleImages.add(fragmentBase.getTitleImage());
        }
    }

    public ArrayList<String> getTitle(){
        return titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.titles.get(position);
    }
}
