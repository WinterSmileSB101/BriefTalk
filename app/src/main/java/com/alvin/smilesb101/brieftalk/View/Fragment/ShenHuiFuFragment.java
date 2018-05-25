package com.alvin.smilesb101.brieftalk.View.Fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvin.smilesb101.brieftalk.Bean.ShenHuiFuBean;
import com.alvin.smilesb101.brieftalk.Presenter.ShowApiPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Adapter.ShenHuiFuRecyclerAdapter;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.IShenHuiFuView;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.FragmentShenHuiFuBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShenHuiFuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShenHuiFuFragment extends FragmentBase implements IShenHuiFuView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = ShenHuiFuFragment.class.getSimpleName();

    FragmentShenHuiFuBinding binding;
    ShowApiPresenter showApiPresenter;
    ShenHuiFuRecyclerAdapter adapter;

    RefreshLayout refreshLayout;
    int page = 1;


    public ShenHuiFuFragment() {
        // Required empty public constructor
        title = "神回复";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShenHuiFuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShenHuiFuFragment newInstance(String param1, String param2) {
        ShenHuiFuFragment fragment = new ShenHuiFuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ShenHuiFuFragment newInstance(Bundle bundle) {
        ShenHuiFuFragment fragment = new ShenHuiFuFragment();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_shen_hui_fu, container, false);
        rootView = binding.getRoot();
        rootContext = binding.getRoot().getContext();
        bindValue();
        initView();
        initValue();

        return rootView;
    }

    private void bindValue() {
        LinearLayoutManager layout = new LinearLayoutManager(this.rootContext,LinearLayoutManager.VERTICAL,false);
        binding.contentView.setLayoutManager(layout);

        showApiPresenter = new ShowApiPresenter(this);
        showApiPresenter.getShenHuiFu(20,1);
    }

    private void initView() {
        refreshLayout = binding.getRoot().findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //刷新，
                page = 1;
                showApiPresenter.getShenHuiFu(20,1);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                showApiPresenter.getShenHuiFu(20,page);
            }
        });
    }

    private void initValue() {
    }

    @Override
    public void showShenHuiFu(ArrayList<ShenHuiFuBean> beans) {
        adapter = new ShenHuiFuRecyclerAdapter(beans,this,binding.contentView);
        binding.contentView.setAdapter(adapter);
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
        ToastUtils.show(rootContext,error);
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
