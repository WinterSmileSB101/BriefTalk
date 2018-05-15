package com.alvin.smilesb101.brieftalk.View.CustomView;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;

public class CustomSwipeRefreshLayout extends SwipeRefreshLayout{

    public interface onUpLoadListener{

    }

    int mTouchSlop;
    onUpLoadListener onUpLoadListener;
    View mfootloadView;
    boolean isLoading = false;
    int mLastDown,mLast;
    RecyclerView recyclerView;

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(canLoad())
            {
            }
        }
    };


    public CustomSwipeRefreshLayout(Context context){
        super(context);
    }

    public CustomSwipeRefreshLayout(Context context,AttributeSet attrs){
        super(context,attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //mfootloadView = LayoutInflater.from(context)
        //.inflate(R.layout.recycler_view_footer,null,false);
    }

    @Override
    protected void onLayout(boolean changed,int left,int top,int right,int bottom){
        super.onLayout(changed,left,top,right,bottom);
        if(recyclerView==null)
        {
            getRecyclerView();
        }
    }


    /**
     * 如果是底部，而且向上拉，而且没有加载标志则可以加载
     * @return
     */
    boolean canLoad()
    {
        return true;
    }

    void getRecyclerView()
    {
        if(getChildCount() > 0)
        {
            if(getChildAt(0) instanceof RecyclerView)
            {
                recyclerView = (RecyclerView)getChildAt(0);
                recyclerView.removeOnScrollListener(scrollListener);
                recyclerView.addOnScrollListener(scrollListener);
            }
        }
    }
}

