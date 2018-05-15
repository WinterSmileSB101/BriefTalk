package com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvin.smilesb101.brieftalk.View.Utils.StaticUtils;

public class FragmentBase extends Fragment {

    protected View rootView;
    protected Context rootContext;
    protected String TAG;
    protected String title;
    protected int titleImage;
    protected Context ContextWrapper;

    protected static final String TITLE_KEY = "title";
    protected static final String TITLEIMAGE_KEY = "titleImage";

    public FragmentBase()
    {
        TAG = getClass().getName();
    }

    public static FragmentBase newInstance(){
        Bundle args = new Bundle();

        FragmentBase fragmentBase = new FragmentBase();
        fragmentBase.setArguments(args);
        return fragmentBase;
    }

    public String getTitle(){
        return title;
    }

    public int getTitleImage(){
        return titleImage;
    }

    protected void setSelfThemeWrapper(){
        this.ContextWrapper = StaticUtils.STATIC_UTILS.getTheme(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.setSelfThemeWrapper();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}

