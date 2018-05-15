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

import com.alvin.smilesb101.brieftalk.Bean.ZhiHuLastNewsBean;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Fragment.DictionaryFragment;
import com.alvin.smilesb101.brieftalk.View.Fragment.TranslateFragment;
import com.android.databinding.library.baseAdapters.BR;
import com.youdao.sdk.ydtranslate.Translate;

public class MoreTranslationRecyclerAdapter extends RecyclerView.Adapter{
    String TAG = "MoreTranslationRecyclerAdapter";
    View rootView;
    Context context;
    Translate translate;
    Activity appCompatActivity;
    TranslateFragment translateFragment;
    RecyclerView recyclerView;

    public MoreTranslationRecyclerAdapter(Translate translate,TranslateFragment fragment,RecyclerView recyclerView){
        appCompatActivity = fragment.getActivity();
        translateFragment = fragment;
        this.recyclerView = recyclerView;
        this.translate = translate;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewDataBinding binding = null;
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_moretranstion_item,parent,false);
        rootView = binding.getRoot();
        MoreTranslationViewHolder viewHolder = new MoreTranslationViewHolder(rootView);
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MoreTranslationViewHolder translationViewHolder = (MoreTranslationViewHolder) holder;
        final String translation = translate.getWebExplains().get(position).getMeans().get(0);
        translationViewHolder.getBinding().setVariable(BR.translation,translation);
        translationViewHolder.getBinding().setVariable(BR.item,translate);

        translationViewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return translate.getWebExplains().size();
    }

    class MoreTranslationViewHolder extends RecyclerView.ViewHolder{


        public MoreTranslationViewHolder(View itemView) {
            super(itemView);
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
