package com.alvin.smilesb101.brieftalk.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.alvin.smilesb101.brieftalk.Bean.BaiSiBuDeJieCommentBean;
import com.alvin.smilesb101.brieftalk.Bean.BaiSiBuDeUserInfoBean;
import com.alvin.smilesb101.brieftalk.Presenter.IDataApiPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.BaseActivity.ThemeBaseActivity;
import com.alvin.smilesb101.brieftalk.View.Adapter.CommentsRecyclerAdapter;
import com.alvin.smilesb101.brieftalk.View.Interface.Activity.ICommentsView;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.ActivityCommentsBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class CommentsActivity extends ThemeBaseActivity implements ICommentsView {

    IDataApiPresenter dataApiPresenter;
    ActivityCommentsBinding binding;
    RecyclerView recycler;
    CommentsRecyclerAdapter commentsRecyclerAdapter;
    Context rootContext;

    RefreshLayout refreshLayout;
    int page = 1;
    String articleId;
    RecyclerView recyclerView;

    static String TAG = CommentsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_comments);
        rootContext = this;
        recycler = findViewById(R.id.commentsRecyclerView);
        refreshLayout = findViewById(R.id.refreshLayout);
        LinearLayoutManager layout = new LinearLayoutManager(rootContext,LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(layout);
        dataApiPresenter = new IDataApiPresenter(this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();


        if(intent!=null)
        {
            articleId = intent.getStringExtra("articleId");
            Log.i(TAG, "onCreate: "+articleId);
            dataApiPresenter.GetBaiSiDuDeJie(articleId);
        }

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //刷新，
                dataApiPresenter.GetBaiSiDuDeJie(articleId);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ToastUtils.show(rootContext,"没有更多了哟");
                refreshlayout.finishLoadmore(/*,false*/);//传入false表示加载失败
            }
        });
    }

    @Override
    public void ShowUsers(ArrayList<BaiSiBuDeUserInfoBean> bingToday) {

    }

    @Override
    public void ShowComments(ArrayList<BaiSiBuDeJieCommentBean> bingToday) {
        commentsRecyclerAdapter = new CommentsRecyclerAdapter(bingToday,new ArrayList<BaiSiBuDeUserInfoBean>(),this,recycler);
        recycler.setAdapter(commentsRecyclerAdapter);
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
        ToastUtils.show(this,"服务器被黑洞吃掉了额.\n"+error);
    }
}
