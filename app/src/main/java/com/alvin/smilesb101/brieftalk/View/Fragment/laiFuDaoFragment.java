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

import com.alvin.smilesb101.brieftalk.Bean.LaiFuDaoJoke;
import com.alvin.smilesb101.brieftalk.Bean.laiFuDaoJokeImage;
import com.alvin.smilesb101.brieftalk.Presenter.LaiFuDaoPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Adapter.LaifuDaoJokeAdapter;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.ILaiFuDaoFragmentView;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.FragmentLaiFuDaoBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link laiFuDaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class laiFuDaoFragment extends FragmentBase implements ILaiFuDaoFragmentView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = laiFuDaoFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentLaiFuDaoBinding binding;
    private LaifuDaoJokeAdapter adapter;
    private LaiFuDaoPresenter presenter;

    RefreshLayout refreshLayout;

    RecyclerView recyclerView;


    public laiFuDaoFragment() {
        // Required empty public constructor
        title = "笑话";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment laiFuDaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static laiFuDaoFragment newInstance(String param1, String param2) {
        laiFuDaoFragment fragment = new laiFuDaoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static laiFuDaoFragment newInstance(Bundle bundle) {
        laiFuDaoFragment fragment = new laiFuDaoFragment();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_lai_fu_dao,container, false);
        rootContext = binding.getRoot().getContext();
        rootView = binding.getRoot();
        bindValue();
        initView();
        initValue();
        return rootView;
    }

    private void bindValue() {
        recyclerView = rootView.findViewById(R.id.contentView);
        LinearLayoutManager layout = new LinearLayoutManager(rootContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layout);
        presenter = new LaiFuDaoPresenter(this);
        presenter.getLaiFuDaoJoke();
    }

    private void initView() {

        refreshLayout = binding.getRoot().findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //刷新，
                presenter.getLaiFuDaoJoke();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ToastUtils.show(rootContext,"没有更多了哟");
                refreshlayout.finishLoadmore(/*,false*/);//传入false表示加载失败
            }
    });
    }

    private void initValue() {
    }

    @Override
    public void onJokeSuccess(ArrayList<LaiFuDaoJoke> laiFuDaoJokes) {
        adapter = new LaifuDaoJokeAdapter(laiFuDaoJokes,this,recyclerView);
        binding.contentView.setAdapter(adapter);
        refreshLayout.finishRefresh(/*,false*/);//传入false表示刷新失败
    }

    @Override
    public void onJokeIamageSuccess(ArrayList<laiFuDaoJokeImage> laiFuDaoJokeImages) {

    }

    @Override
    public void onError(String error) {
        Log.i(TAG, "onError: 返回错误"+error);
        ToastUtils.show(rootContext,error);
    }
}
