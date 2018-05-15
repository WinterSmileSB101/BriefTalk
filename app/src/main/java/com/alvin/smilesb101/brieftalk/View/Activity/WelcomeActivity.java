package com.alvin.smilesb101.brieftalk.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvin.smilesb101.brieftalk.Bean.BingTodayBean;
import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean._User;
import com.alvin.smilesb101.brieftalk.Presenter.BingTodayPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Interface.Activity.IWelcomeView;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.ActivityWecomeBinding;
import com.bumptech.glide.Glide;

import cn.bmob.v3.BmobUser;

public class WelcomeActivity extends AppCompatActivity implements IWelcomeView {

    String TAG = WelcomeActivity.class.getSimpleName();

    Context context;

    BingTodayPresenter bingTodayPresenter;
    ActivityWecomeBinding binding;
    ImageView bingTodayImage;
    TextView title,subTitle,desc,waitText;

    CountDownTimer timer = new CountDownTimer(5000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            waitText.setText(""+((millisUntilFinished/1000)-1));
        }

        @Override
        public void onFinish() {
            _User user = BmobUser.getCurrentUser(context,_User.class);
            Log.i(TAG, "onFinish: "+user);
            if(user!=null)
            {
                Log.i(TAG, "run: "+user.getUsername());
                //打开主界面
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                intent.putExtra("userInfo",user);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
            else
            {
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                WelcomeActivity.this.finish();
            }
        }
    };

    Handler handler;
    int watiTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_wecome);
        initView();
        initValue();
    }

    void initView(){
        context = this;
        bingTodayImage = binding.imageToday;
        title = binding.title;
        subTitle = binding.subTitle;
        desc = binding.desc;
        waitText = binding.waitText;
        waitText.setText("4");
        Glide.with(this)
                .load(R.mipmap.back_ground)
                .into(bingTodayImage);
    }

    void initValue(){
        bingTodayPresenter = new BingTodayPresenter(this);
        bingTodayPresenter.getBingToday();
        timer.start();
    }

    @Override
    public void ShowTodayBing(BingTodayBean bingToday) {
        //title.setText(bingToday.getTitle());
        subTitle.setText(bingToday.getCopyright());
        //desc.setText(bingToday.getDescription());
        Log.i(TAG, "ShowTodayBing: "+bingToday.getImg_1920());

        Glide.with(this)
                    .load(bingToday.getImg_1920())
                    .into(bingTodayImage);

    }

    @Override
    public void onError(String error) {
        Log.i(TAG, "onError: 返回错误--"+error);
        ToastUtils.show(this,error);
    }
}
