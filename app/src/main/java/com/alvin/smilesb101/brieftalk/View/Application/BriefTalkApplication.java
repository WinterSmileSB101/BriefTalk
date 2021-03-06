package com.alvin.smilesb101.brieftalk.View.Application;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alvin.smilesb101.brieftalk.View.Utils.Helper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.youdao.sdk.app.YouDaoApplication;

import java.io.IOException;

import cn.bmob.v3.Bmob;

public class BriefTalkApplication extends Application {
    private static BriefTalkApplication briefTalkApplication;
    private Context context;
    static final String TAG=BriefTalkApplication.class.getSimpleName();

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new PhoenixHeader(context);
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        /*
        //复制文件到 SD卡
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(TAG, "run: 复制文件");
                    Helper.copyLanDataToPhoneDir(context, "tessdata");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
        if(YouDaoApplication.getApplicationContext() == null)
            YouDaoApplication.init(this, "5b73124f2ba4b097");
//          //创建应用，每个应用都会有一个Appid，绑定对应的翻译服务实例，即可使用
        //初始化后端云
        Bmob.initialize(this,"93493eb990bcb33993957a94029816dc");
        briefTalkApplication = this;
    }

    public static BriefTalkApplication getInstance() {
        return briefTalkApplication;
    }

}
