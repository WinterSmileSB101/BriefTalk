package com.alvin.smilesb101.brieftalk.View.Activity.BaseActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alvin.smilesb101.brieftalk.View.Utils.StaticUtils;

public class ThemeBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StaticUtils.STATIC_UTILS.setTheme(this);
        super.onCreate(savedInstanceState);
    }
}
