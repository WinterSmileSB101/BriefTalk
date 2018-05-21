package com.alvin.smilesb101.brieftalk.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvin.smilesb101.brieftalk.Bean.HistoryTodayBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuLastNewsBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuStory;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.ZhiHuDetailActivity;
import com.alvin.smilesb101.brieftalk.View.Fragment.DictionaryFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static com.alvin.smilesb101.brieftalk.BR.item;
import static com.android.databinding.library.baseAdapters.BR.ZhiHuNewsItem;

public class HistoryTodayRecyclerAdapter extends RecyclerView.Adapter {
    String TAG = "HistoryTodayRecyclerAdapter";
    View rootView;
    Context context;
    ArrayList<HistoryTodayBean> historyTodayBeans;
    Activity appCompatActivity;
    DictionaryFragment dictionaryFragment;
    RecyclerView recyclerView;


    public HistoryTodayRecyclerAdapter(ArrayList<HistoryTodayBean> historyTodayBeans,Activity activity,RecyclerView recyclerView)
    {
        Log.i(TAG, "HistoryTodayRecyclerAdapter: "+historyTodayBeans.size());
        this.appCompatActivity = activity;
        this.historyTodayBeans = historyTodayBeans;
        this.recyclerView = recyclerView;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        ViewDataBinding dataBinding = null;

        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recycler_historytoday_item,parent,false);
        rootView = dataBinding.getRoot();
        HistodayTodayViewHolder viewHolder = new HistodayTodayViewHolder(rootView);
        viewHolder.setBinding(dataBinding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final HistodayTodayViewHolder viewHolder = (HistodayTodayViewHolder) holder;
        final HistoryTodayBean story = historyTodayBeans.get(position);

        if(viewHolder.imageView!=null&&story!=null) {
            Glide.with(viewHolder.binding.getRoot())
                    .load(story.getImg())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                    .into(viewHolder.imageView);
        }
        viewHolder.getBinding().setVariable(item,story);
        viewHolder.getBinding().executePendingBindings();//提交更改
    }

    @Override
    public int getItemCount() {
        return historyTodayBeans.size();
    }

    class HistodayTodayViewHolder extends RecyclerView.ViewHolder{

        private ViewDataBinding binding;
        private AppCompatImageView imageView;
        public View rootView;
        public Context rootContext;

        public HistodayTodayViewHolder(final View itemView) {
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
