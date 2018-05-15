package com.alvin.smilesb101.brieftalk.View.Adapter.Base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;

import java.util.ArrayList;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    protected ArrayList<FragmentBase> fragmentBases;
    protected ArrayList<String> titles;
    protected ArrayList<Integer> titleImages;

    public BaseFragmentPagerAdapter(FragmentManager fm, ArrayList<FragmentBase> fragmentBaseArrayList){
        super(fm);
        fragmentBases = fragmentBaseArrayList;
        if(fragmentBaseArrayList==null)
        {
            fragmentBases = new ArrayList<>();
        }
        titles = new ArrayList<>();

    }
    public BaseFragmentPagerAdapter(FragmentManager fm)
    {
        super(fm);
        fragmentBases = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position){
        return fragmentBases.get(position);
    }

    @Override
    public int getCount(){
        return fragmentBases.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return fragmentBases.get(position).getTitle();
    }

    public void setFragmentBases(ArrayList<FragmentBase> fragmentBases){
        this.fragmentBases = fragmentBases;
        notifyDataSetChanged();
    }

    public void addFragmentBases(FragmentBase fragmentBase)
    {
        this.fragmentBases.add(fragmentBase);
        notifyDataSetChanged();
    }
}

