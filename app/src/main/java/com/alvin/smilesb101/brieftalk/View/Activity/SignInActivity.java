package com.alvin.smilesb101.brieftalk.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean.UserInfo;
import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean._User;
import com.alvin.smilesb101.brieftalk.Bean.HistoryTodayBean;
import com.alvin.smilesb101.brieftalk.Presenter.ShowApiPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.BaseActivity.ThemeBaseActivity;
import com.alvin.smilesb101.brieftalk.View.Adapter.HistoryTodayRecyclerAdapter;
import com.alvin.smilesb101.brieftalk.View.Interface.IHistoryTodayView;
import com.alvin.smilesb101.brieftalk.View.Utils.Helper;
import com.alvin.smilesb101.brieftalk.View.Utils.StaticUtils;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.ActivitySignInBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Calendar;

public class SignInActivity extends AppCompatActivity implements IHistoryTodayView{

    ActivitySignInBinding binding;

    ShowApiPresenter showApiPresenter;

    Context context;

    HistoryTodayRecyclerAdapter historyTodayRecyclerAdapter;

    UserInfo userInfo;

    TextView signInDateText;

    static final String TAG =  SignInActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in);
        binding.headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignInActivity.this.onBackPressed();
            }
        });

        binding.setTitleText("语签");
        signInDateText = binding.getRoot().findViewById(R.id.signInDate);
        Intent intent = getIntent();
        if(intent!=null) {
            userInfo = (UserInfo) intent.getSerializableExtra("userInfo");
            if(userInfo!=null)
            {
                userInfo.setSignInTime(userInfo.getSignInTime()+1);
                userInfo.update(this);
                signInDateText.setText(userInfo.getSignInTime()+"");
            }
        }
        Calendar calendar = Calendar.getInstance();
        binding.month.setText(calendar.get(Calendar.MONTH) + 1+"");
        binding.day.setText(calendar.get(Calendar.DAY_OF_MONTH)+"");

        context = binding.getRoot().getContext();

        LinearLayoutManager layout = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        binding.recyclerView.setLayoutManager(layout);
        binding.recyclerView.setNestedScrollingEnabled(false);

        showApiPresenter = new ShowApiPresenter(this);
        showApiPresenter.getHistoryToday(null);
    }

    @Override
    public void ShowHistoryToday(ArrayList<HistoryTodayBean> bingToday) {
        historyTodayRecyclerAdapter  = new HistoryTodayRecyclerAdapter(bingToday,this,binding.recyclerView);
        binding.recyclerView.setAdapter(historyTodayRecyclerAdapter);

        for (HistoryTodayBean bean:bingToday) {
            if(bean.getImg()!=null)
            {
                binding.todayWords.setText(bean.getTitle());
                binding.date.setText("-- At "+bean.getYear()+"."+bean.getMonth()+"."+bean.getDay());
                Glide.with(this)
                        .load(bean.getImg())
                        .into(binding.iamgeView);
                Glide.with(this)
                        .load(bean.getImg())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                BitmapDrawable bd = (BitmapDrawable) resource;

                                getBitmapMainColor(bd.getBitmap());
                            }
                        });
                break;
            }
        }




    }

    @Override
    public void onError(String error) {
        ToastUtils.show(this,error);
    }

    void getBitmapMainColor(Bitmap bitmap){
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@NonNull Palette palette) {
                Palette.Swatch swatch = palette.getDarkMutedSwatch();
                if(swatch!=null)
                {
                    binding.getRoot().findViewById(R.id.contentPanel).setBackgroundColor(swatch.getRgb());
                    //binding.toolbar.setBackgroundColor(swatch.getRgb());
                    //binding.historyTodayPanel.setBackgroundColor(swatch.getRgb());
                }
            }
        });
    }
}
