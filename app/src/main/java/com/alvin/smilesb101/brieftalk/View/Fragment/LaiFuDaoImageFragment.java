package com.alvin.smilesb101.brieftalk.View.Fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvin.smilesb101.brieftalk.Bean.LaiFuDaoJoke;
import com.alvin.smilesb101.brieftalk.Bean.laiFuDaoJokeImage;
import com.alvin.smilesb101.brieftalk.Presenter.LaiFuDaoPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Adapter.LaifuDaoJokeAdapter;
import com.alvin.smilesb101.brieftalk.View.Adapter.LaifuDaoJokeImageAdapter;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.ILaiFuDaoFragmentView;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.FragmentLaiFuDaoImageBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LaiFuDaoImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaiFuDaoImageFragment extends FragmentBase implements ILaiFuDaoFragmentView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = laiFuDaoFragment.class.getSimpleName();

    FragmentLaiFuDaoImageBinding binding;
    private LaifuDaoJokeImageAdapter adapter;
    private LaiFuDaoPresenter presenter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RefreshLayout refreshLayout;

    RecyclerView recyclerView;


    public LaiFuDaoImageFragment() {
        // Required empty public constructor
        title = "趣图";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LaiFuDaoImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LaiFuDaoImageFragment newInstance(String param1, String param2) {
        LaiFuDaoImageFragment fragment = new LaiFuDaoImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static LaiFuDaoImageFragment newInstance(Bundle bundle) {
        LaiFuDaoImageFragment fragment = new LaiFuDaoImageFragment();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_lai_fu_dao_image, container, false);
        rootView = binding.getRoot();
        bindValue();
        initView();
        initValue();
        return rootView;
    }

    private void bindValue() {
        recyclerView = rootView.findViewById(R.id.contentView);
        LinearLayoutManager layout = new LinearLayoutManager(this.rootContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layout);

        presenter = new LaiFuDaoPresenter(this);
        presenter.getLaiFuDaoJokeImage();

    }

    private void initView() {
        refreshLayout = binding.getRoot().findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //刷新，
                presenter.getLaiFuDaoJokeImage();
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

    }

    @Override
    public void onJokeIamageSuccess(ArrayList<laiFuDaoJokeImage> laiFuDaoJokeImages) {
        adapter = new LaifuDaoJokeImageAdapter(laiFuDaoJokeImages,this,recyclerView);
        recyclerView.setAdapter(adapter);
        refreshLayout.finishRefresh(/*,false*/);//传入false表示刷新失败
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
