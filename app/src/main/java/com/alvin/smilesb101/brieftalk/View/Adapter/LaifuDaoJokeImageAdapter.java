package com.alvin.smilesb101.brieftalk.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvin.smilesb101.brieftalk.BR;
import com.alvin.smilesb101.brieftalk.Bean.LaiFuDaoJoke;
import com.alvin.smilesb101.brieftalk.Bean.laiFuDaoJokeImage;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Fragment.LaiFuDaoImageFragment;
import com.alvin.smilesb101.brieftalk.View.Fragment.laiFuDaoFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LaifuDaoJokeImageAdapter extends RecyclerView.Adapter {
    ArrayList<laiFuDaoJokeImage> laiFuDaoJokes;

    RecyclerView recyclerView;
    Activity activity;
    Context context;
    LaiFuDaoImageFragment fuDaoFragment;
    View rootView;

    public LaifuDaoJokeImageAdapter(ArrayList<laiFuDaoJokeImage> jokes, LaiFuDaoImageFragment fuDaoFragment, RecyclerView recyclerView)
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

        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recycler_laifudao_image_item,parent,false);
        rootView = dataBinding.getRoot();
        JokeImageViewHolder viewHolder = new JokeImageViewHolder(rootView);
        viewHolder.setBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        JokeImageViewHolder viewHolder = (JokeImageViewHolder) holder;
        final laiFuDaoJokeImage joke = laiFuDaoJokes.get(position);
        Glide.with(activity)
                .load(joke.sourceurl)
                .into(viewHolder.contentImage);
        viewHolder.content.setText(Html.fromHtml(joke.title));
        viewHolder.getBinding().setVariable(BR.item,joke);
        viewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return laiFuDaoJokes.size();
    }

    public class JokeImageViewHolder extends RecyclerView.ViewHolder{
        public ImageView contentImage;
        public TextView content;

        public JokeImageViewHolder(View itemView) {
            super(itemView);
            contentImage = itemView.findViewById(R.id.contentImage);
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
