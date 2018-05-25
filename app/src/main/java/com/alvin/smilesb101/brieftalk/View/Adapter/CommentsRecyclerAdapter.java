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
import android.widget.TextView;

import com.alvin.smilesb101.brieftalk.Bean.BaiSiBuDeJieCommentBean;
import com.alvin.smilesb101.brieftalk.Bean.BaiSiBuDeUserInfoBean;
import com.alvin.smilesb101.brieftalk.R;

import java.util.ArrayList;

public class CommentsRecyclerAdapter extends RecyclerView.Adapter {

    ArrayList<BaiSiBuDeJieCommentBean> deJieCommentBeans;
    ArrayList<BaiSiBuDeUserInfoBean> userInfoBeans;
    Activity activity;
    RecyclerView recyclerView;
    Context context;
    View rootView;

    public CommentsRecyclerAdapter(ArrayList<BaiSiBuDeJieCommentBean> deJieCommentBeans,ArrayList<BaiSiBuDeUserInfoBean> userInfoBeans, Activity activity, RecyclerView recyclerView) {
        this.deJieCommentBeans = deJieCommentBeans;
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        ViewDataBinding dataBinding = null;
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recycler_comment_item,parent,false);
        rootView = dataBinding.getRoot();
        CommentViewHolder holder = new CommentViewHolder(rootView);
        holder.setBinding(dataBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommentViewHolder viewHolder = (CommentViewHolder) holder;
        final BaiSiBuDeJieCommentBean bean = deJieCommentBeans.get(position);
        viewHolder.content.setText(bean.getContent());
        viewHolder.likes.setText(bean.getLikeCount()+"");
        viewHolder.userName.setText(bean.getCommenterScreenName());
        //viewHolder.getBinding().setVariable(BR.item,bean);

        viewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return deJieCommentBeans.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        public TextView userName,publishDate,likes,content;
        public ImageView userHeader;

        public CommentViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            userName = itemView.findViewById(R.id.userName);
            publishDate = itemView.findViewById(R.id.publishDate);
            likes = itemView.findViewById(R.id.likes);

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
