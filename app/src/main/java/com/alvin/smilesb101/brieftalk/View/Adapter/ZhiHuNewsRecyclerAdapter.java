package com.alvin.smilesb101.brieftalk.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvin.smilesb101.brieftalk.Bean.ZhiHuLastNewsBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuStory;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.ZhiHuDetailActivity;
import com.alvin.smilesb101.brieftalk.View.Fragment.DictionaryFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.Serializable;
import java.util.ArrayList;

import static com.android.databinding.library.baseAdapters.BR.ZhiHuNewsItem;


public class ZhiHuNewsRecyclerAdapter extends RecyclerView.Adapter {

    String TAG = "ZhiHuNewsRecyclerAdapter";
    View rootView;
    Context context;
    ZhiHuLastNewsBean lastNewsBean;
    Activity appCompatActivity;
    DictionaryFragment dictionaryFragment;
    RecyclerView recyclerView;


    public ZhiHuNewsRecyclerAdapter(ZhiHuLastNewsBean bean,DictionaryFragment fragment,RecyclerView recyclerView)
    {
        this.appCompatActivity = fragment.getActivity();
        this.lastNewsBean = bean;
        this.recyclerView = recyclerView;
        this.dictionaryFragment = fragment;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        ViewDataBinding dataBinding = null;

        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recycler_zhihu_news_item,parent,false);
        rootView = dataBinding.getRoot();
        ZhiHuNewsViewHolder viewHolder = new ZhiHuNewsViewHolder(rootView);
        viewHolder.setBinding(dataBinding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ZhiHuNewsViewHolder viewHolder = (ZhiHuNewsViewHolder) holder;
        final ZhiHuStory story = lastNewsBean.stories.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //进入,打开新界面显示详情新闻界面
                Intent intent = new Intent(viewHolder.rootContext,ZhiHuDetailActivity.class);//新建Intent
                intent.putExtra(ZhiHuDetailActivity.Item_KEY, story);//传递对象

                viewHolder.rootContext.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(appCompatActivity,v,"newsImage").toBundle());//打开新活动
            }
        });
        if(viewHolder.imageView!=null&&story!=null) {
            Glide.with(viewHolder.binding.getRoot())
                    .load(story.images.get(0))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                    .into(viewHolder.imageView);
        }
        viewHolder.getBinding().setVariable(ZhiHuNewsItem,story);
        viewHolder.getBinding().executePendingBindings();//提交更改
    }

    @Override
    public int getItemCount() {
        return lastNewsBean.stories.size();
    }

    class ZhiHuNewsViewHolder extends RecyclerView.ViewHolder{

        private ViewDataBinding binding;
        private AppCompatImageView imageView;
        public View rootView;
        public Context rootContext;

        public ZhiHuNewsViewHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemImage);
            rootContext = itemView.getContext();
            rootView = itemView;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}
