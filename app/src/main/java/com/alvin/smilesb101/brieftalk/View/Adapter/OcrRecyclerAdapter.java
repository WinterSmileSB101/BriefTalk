package com.alvin.smilesb101.brieftalk.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alvin.smilesb101.brieftalk.Bean.OcrReconizeBean;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Fragment.YouDaoOcrFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OcrRecyclerAdapter extends RecyclerView.Adapter {

    private View rootView;
    private Activity appCompatActivity;
    private YouDaoOcrFragment fragment;
    private ArrayList<OcrReconizeBean> reconizeBeans;
    private RecyclerView recyclerView;
    private Context context;

    public OcrRecyclerAdapter(ArrayList<OcrReconizeBean> reconizeBeans,YouDaoOcrFragment fragment,RecyclerView recyclerView)
    {
        this.reconizeBeans = reconizeBeans;
        this.fragment = fragment;
        this.recyclerView = recyclerView;
        appCompatActivity = this.fragment.getActivity();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewDataBinding binding = null;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recycler_youdao_ocr_item,parent,false);
        rootView = binding.getRoot();
        OcrViewHolder ocrViewHolder = new OcrViewHolder(rootView);
        ocrViewHolder.setBinding(binding);
        return ocrViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OcrViewHolder ocrViewHolder = (OcrViewHolder) holder;
        Glide.with(context)
        .load(reconizeBeans.get(position).getUri().getPath())
        .into(ocrViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return reconizeBeans.size();
    }

    public class OcrViewHolder extends RecyclerView.ViewHolder{

        private ViewDataBinding binding;

        public ImageView imageView;

        public OcrViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}
