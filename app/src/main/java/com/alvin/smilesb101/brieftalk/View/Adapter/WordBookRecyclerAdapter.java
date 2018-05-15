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

import com.alvin.smilesb101.brieftalk.BR;
import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean.Word;
import com.alvin.smilesb101.brieftalk.Bean.KingSoftWordBean;
import com.alvin.smilesb101.brieftalk.Presenter.KingSoftPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Fragment.WordBookFragment;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.IWordBookView;

import java.util.ArrayList;

public class WordBookRecyclerAdapter extends RecyclerView.Adapter{

    ArrayList<Word> words;
    Activity activity;
    WordBookFragment fragment;
    RecyclerView recyclerView;
    View rootView;
    Context context;

    public WordBookRecyclerAdapter(ArrayList<Word> words, WordBookFragment fragment, RecyclerView recyclerView) {
        this.words = words;
        this.fragment = fragment;
        this.recyclerView = recyclerView;
        this.activity = fragment.getActivity();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewDataBinding binding = null;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recycler_word_item,parent,false);
        rootView = binding.getRoot();
        WordHolder wordHolder = new WordHolder(rootView);
        wordHolder.setBinding(binding);
        return wordHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WordHolder wordHolder = (WordHolder) holder;
        final Word word = words.get(position);
        wordHolder.getBinding().setVariable(BR.baseWord,word);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class WordHolder extends RecyclerView.ViewHolder{
        public WordHolder(View itemView) {
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
