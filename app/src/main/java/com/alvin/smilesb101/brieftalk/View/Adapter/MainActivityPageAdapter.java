package com.alvin.smilesb101.brieftalk.View.Adapter;

import android.support.v4.app.FragmentManager;

import com.alvin.smilesb101.brieftalk.View.Adapter.Base.BaseFragmentPagerAdapter;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;

import java.util.ArrayList;

public class MainActivityPageAdapter extends BaseFragmentPagerAdapter {

    public MainActivityPageAdapter(FragmentManager fm) {
        super(fm);

        fragmentBases = new ArrayList<>();
        //fragmentBases.add();//主页
        //fragmentBases.add();//娱乐
        //fragmentBases.add();//我的

        titles = new ArrayList<>();

        titleImages = new ArrayList<>();
        for (FragmentBase fragmentBase:fragmentBases) {
            titles.add(fragmentBase.getTitle());
            titleImages.add(fragmentBase.getTitleImage());
        }
    }

    public ArrayList<Integer> getTitleImages(){
        return titleImages;
    }

    public ArrayList<String> getTitle(){
        return titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.titles.get(position);
    }
}
