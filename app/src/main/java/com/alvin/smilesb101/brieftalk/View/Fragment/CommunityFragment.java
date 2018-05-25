package com.alvin.smilesb101.brieftalk.View.Fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvin.smilesb101.brieftalk.Bean.ShowBaiSiBuDeBean;
import com.alvin.smilesb101.brieftalk.Presenter.ShowApiPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Adapter.HotCommunityArticleRecyclerAdapter;
import com.alvin.smilesb101.brieftalk.View.CustomView.CustomLinearLayoutManager;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.ICommunityView;
import com.alvin.smilesb101.brieftalk.databinding.FragmentCommunityBinding;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends FragmentBase implements ICommunityView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static final String TAG = CommunityFragment.class.getSimpleName();

    FragmentCommunityBinding binding;
    ShowApiPresenter showApiPresenter;

    HotCommunityArticleRecyclerAdapter articleRecyclerAdapter;

    RefreshLayout refreshLayout;
    int page = 1;

    RecyclerView recyclerView;

    public CommunityFragment() {
        // Required empty public constructor
        title = "圈子";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static CommunityFragment newInstance(Bundle bundle) {
        CommunityFragment fragment = new CommunityFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_community, container, false);
        recyclerView = binding.getRoot().findViewById(R.id.articleList);

        CustomLinearLayoutManager layout = new CustomLinearLayoutManager(this.rootContext);
        layout.setScrollEnabled(false);
        //LinearLayoutManager layout = new LinearLayoutManager(this.rootContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layout);
        rootView = binding.getRoot();
        rootContext = rootView.getContext();

        BindValue();
        return rootView;
    }

    private void BindValue() {
        /*
        binding.floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                Log.i(TAG, "onSearchTextChanged: "+newQuery);
                title = newQuery;
                refreshLayout.setEnableRefresh(true);
            }
        });*/
        showApiPresenter = new ShowApiPresenter(this);

        showApiPresenter.getBSBDJ("10","",1);

        refreshLayout = binding.getRoot().findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //刷新，
                page = 1;
                showApiPresenter.getBSBDJ("10","",1);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                showApiPresenter.getBSBDJ("10","",page);
            }
        });
    }

    @Override
    public void onSuccess(ArrayList<ShowBaiSiBuDeBean> bsbdBeans) {
        Log.i(TAG, "onSuccess: "+bsbdBeans.size());
        articleRecyclerAdapter = new HotCommunityArticleRecyclerAdapter(this,recyclerView,bsbdBeans);
        recyclerView.setAdapter(articleRecyclerAdapter);
        if(refreshLayout.isLoading())
        {
            refreshLayout.finishLoadmore(/*,false*/);//传入false表示加载失败
        }
        else if(refreshLayout.isRefreshing())
        {
            refreshLayout.finishRefresh();
        }
    }

    @Override
    public void onError(String error) {
        Log.i(TAG, "onError: 获取百思不得失败："+error);
        if(refreshLayout.isLoading())
        {
            refreshLayout.finishLoadmore(false);//传入false表示加载失败
        }
        else if(refreshLayout.isRefreshing())
        {
            refreshLayout.finishRefresh(false);
        }
    }
}
