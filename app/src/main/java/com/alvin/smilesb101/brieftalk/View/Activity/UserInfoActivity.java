package com.alvin.smilesb101.brieftalk.View.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean.UserInfo;
import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean._User;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.databinding.ActivityUserInfoBinding;
import com.bumptech.glide.Glide;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    UserInfo userInfo;
    _User user;
    ActivityUserInfoBinding binding;

    static final String TAG = UserInfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_info);

        binding.setTitle("个人信息");

        Toolbar toolbar = binding.getRoot().findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent!=null) {
            userInfo = (UserInfo) intent.getSerializableExtra("userInfo");
            if(userInfo!=null)
            {
                binding.searchTime.setText(userInfo.getSearchTime()+"");
                binding.wordsCount.setText(userInfo.getWordsCount()+"");
                binding.readTime.setText(userInfo.getReadTime()+"");

            }
            user = (_User) intent.getSerializableExtra("user");
            if(user!=null)
            {
                Glide.with(this)
                        .load(user.userHeader)
                        .into(binding.userHeaderImage);

                Glide.with(this)
                        .load(user.userHeader)
                        .into(binding.userHeader);

                binding.userName.setText(user.getUsername());
                binding.nickName.setText(user.getUsername());
                binding.simpleDes.setText(user.smilpleDes);
                binding.sex.setText(user.gender);
                binding.birthday.setText(user.birthday);
                binding.education.setText(user.education);
                binding.school.setText(user.school);
                binding.region.setText(user.region);
                binding.carrer.setText(user.carrer);
                binding.getRoot().findViewById(R.id.logout).setOnClickListener(this);
            }
        }
    }

    @Override
    public void onBackPressed() {
        //finishAfterTransition();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.logout:
                _User.logOut(this);
                //导向登录页面
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
