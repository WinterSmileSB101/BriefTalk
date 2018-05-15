package com.alvin.smilesb101.brieftalk.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alvin.smilesb101.brieftalk.BR;
import com.alvin.smilesb101.brieftalk.Bean.ShowBaiSiBuDeBean;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.CustomView.RoundImageView;
import com.alvin.smilesb101.brieftalk.View.Fragment.CommunityFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HotCommunityArticleRecyclerAdapter extends RecyclerView.Adapter {

    Activity activity;
    CommunityFragment fragment;
    RecyclerView recyclerView;
    ArrayList<ShowBaiSiBuDeBean> buDeBeans;
    View rootView;
    Context context;

    public HotCommunityArticleRecyclerAdapter(CommunityFragment fragment, RecyclerView recyclerView, ArrayList<ShowBaiSiBuDeBean> buDeBeans) {
        this.fragment = fragment;
        this.recyclerView = recyclerView;
        this.buDeBeans = buDeBeans;
        activity = this.fragment.getActivity();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        ViewDataBinding dataBinding = null;

        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recycler_hot_community_item,parent,false);
        rootView = dataBinding.getRoot();
        HotHolder hotHolder = new HotHolder(rootView);
        hotHolder.setBinding(dataBinding);
        return hotHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HotHolder hotHolder = (HotHolder) holder;
        ShowBaiSiBuDeBean baiSiBuDeBean = buDeBeans.get(position);
        hotHolder.getBinding().setVariable(BR.itemBean,baiSiBuDeBean);

        Glide.with(context).load(baiSiBuDeBean.getProfile_image())
                .into(hotHolder.userHeader);

        Glide.with(context).load(baiSiBuDeBean.getCdn_img())
                .into(hotHolder.itemImage);

        hotHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return buDeBeans.size();
    }

    class HotHolder extends RecyclerView.ViewHolder{

        public ImageView itemImage;
        public RoundImageView userHeader;

        public HotHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            userHeader = itemView.findViewById(R.id.userHeader);
        }

        private ViewDataBinding binding;
        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}
