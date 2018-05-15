package com.alvin.smilesb101.brieftalk.View.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.alvin.smilesb101.brieftalk.View.Adapter.Base.BaseFragmentPagerAdapter;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.View.Fragment.DiscoryFragment;
import com.alvin.smilesb101.brieftalk.View.Fragment.laiFuDaoFragment;

import java.util.ArrayList;

public class DiscoryPageAdapter extends BaseFragmentPagerAdapter {

    ArrayList<FragmentBase> fragmentBases;

    public DiscoryPageAdapter(FragmentManager fm, ArrayList<FragmentBase> fragmentBaseArrayList) {
        super(fm, fragmentBaseArrayList);
        this.fragmentBases = fragmentBaseArrayList;
        titles = new ArrayList<>();

        titleImages = new ArrayList<>();
        for (FragmentBase fragmentBase:fragmentBases) {
            titles.add(fragmentBase.getTitle());
            titleImages.add(fragmentBase.getTitleImage());
        }
    }

    public DiscoryPageAdapter(FragmentManager fm) {
        super(fm);

        fragmentBases = new ArrayList<>();
        fragmentBases.add(laiFuDaoFragment.newInstance(null));//主页
        //fragmentBases.add();//娱乐
        //fragmentBases.add();//我的

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
