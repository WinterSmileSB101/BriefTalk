package com.alvin.smilesb101.brieftalk.View.Adapter.Base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class BaseRecyclerViewAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){

    }

    @Override
    public int getItemCount(){
        return 0;
    }

    public int getViewType(int pos,int listSize)
    {
        if(pos==listSize+1)
        {
            return 1;
        }
        return 0;
    }
}

