package com.alvin.smilesb101.brieftalk.View.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.alvin.smilesb101.brieftalk.Bean.SkinBean;
import com.alvin.smilesb101.brieftalk.Presenter.SkinActivityPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.BaseActivity.ThemeBaseActivity;
import com.alvin.smilesb101.brieftalk.View.Adapter.SkinRecyclerViewAdapter;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.ISkinActivityView;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.ActivitySkinBinding;

import java.util.ArrayList;

public class SkinActivity extends ThemeBaseActivity implements ISkinActivityView {

    SkinActivityPresenter skinActivityPresenter;
    RecyclerView recyclerView;
    ActivitySkinBinding binding;

    static final String TAG = SkinActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        RecyclerView recyclerView;
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initValue();
    }

    void initValue()
    {
        recyclerView = findViewById(R.id.official_recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        skinActivityPresenter = new SkinActivityPresenter(this);
        skinActivityPresenter.getSkinList();//获取
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
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
    public void onOfficalSkinBcak(ArrayList<SkinBean> officalSkin){
        Log.i(TAG, "onOfficalSkinBcak: "+officalSkin.size());
        recyclerView.setAdapter(new SkinRecyclerViewAdapter(officalSkin,this));
    }

    @Override
    public void onError(String error){
        ToastUtils.show(this,"错误："+error);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//这里设置这个tag可以让活动重新创建
        startActivity(intent);
        finish();
    }
}
