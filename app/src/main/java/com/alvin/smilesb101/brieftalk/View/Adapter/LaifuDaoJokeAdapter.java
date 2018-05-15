package com.alvin.smilesb101.brieftalk.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alvin.smilesb101.brieftalk.BR;
import com.alvin.smilesb101.brieftalk.Bean.LaiFuDaoJoke;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Fragment.laiFuDaoFragment;

import java.util.ArrayList;

public class LaifuDaoJokeAdapter extends RecyclerView.Adapter {

    ArrayList<LaiFuDaoJoke> laiFuDaoJokes;

    RecyclerView recyclerView;
    Activity activity;
    Context context;
    laiFuDaoFragment fuDaoFragment;
    View rootView;

    public LaifuDaoJokeAdapter(ArrayList<LaiFuDaoJoke> jokes,laiFuDaoFragment fuDaoFragment,RecyclerView recyclerView)
    {
        this.activity = fuDaoFragment.getActivity();
        this.recyclerView = recyclerView;
        this.fuDaoFragment = fuDaoFragment;
        context = fuDaoFragment.getContext();
        laiFuDaoJokes = jokes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        ViewDataBinding dataBinding = null;

        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recycler_laifudao_item,parent,false);
        rootView = dataBinding.getRoot();
        JokeViewHolder viewHolder = new JokeViewHolder(rootView);
        viewHolder.setBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        JokeViewHolder viewHolder = (JokeViewHolder) holder;
        final LaiFuDaoJoke joke = laiFuDaoJokes.get(position);
        viewHolder.getBinding().setVariable(BR.item,joke);
        viewHolder.content.setText(Html.fromHtml(joke.content));
        viewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return laiFuDaoJokes.size();
    }

    public class JokeViewHolder extends RecyclerView.ViewHolder{

        public TextView content;

        public JokeViewHolder(View itemView) {
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
