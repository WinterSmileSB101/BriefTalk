package com.alvin.smilesb101.brieftalk.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alvin.smilesb101.brieftalk.BR;
import com.alvin.smilesb101.brieftalk.Bean.ShenHuiFuBean;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Fragment.ShenHuiFuFragment;

import java.util.ArrayList;

public class ShenHuiFuRecyclerAdapter extends RecyclerView.Adapter {
    ArrayList<ShenHuiFuBean> beans;
    Activity activity;
    ShenHuiFuFragment fragment;
    RecyclerView recyclerView;
    Context context;
    View rootView;

    public ShenHuiFuRecyclerAdapter(ArrayList<ShenHuiFuBean> beans, ShenHuiFuFragment fragment, RecyclerView recyclerView) {
        this.beans = beans;
        this.fragment = fragment;
        this.recyclerView = recyclerView;
        activity = this.fragment.getActivity();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        ViewDataBinding dataBinding = null;
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recycler_shenhuifu_item,parent,false);
        rootView = dataBinding.getRoot();
        ShenHuiFuViewHolder viewHolder = new ShenHuiFuViewHolder(rootView);
        viewHolder.setBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShenHuiFuViewHolder viewHolder = (ShenHuiFuViewHolder) holder;
        final ShenHuiFuBean bean = beans.get(position);

        bean.content.replace(bean.title,"");
        /*int num = 1;
        while (bean.content.matches("\\d+.")){
            bean.content.replaceFirst("\\d+.","<br/>"+num+" .");
        }*/
        //bean.content.replaceAll("\\d+.","<br/>");
        viewHolder.content.setText(Html.fromHtml(bean.content));
        viewHolder.getBinding().setVariable(BR.item,bean);

        viewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    class ShenHuiFuViewHolder extends RecyclerView.ViewHolder{
        public TextView content;
        public ShenHuiFuViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.contentText);
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
