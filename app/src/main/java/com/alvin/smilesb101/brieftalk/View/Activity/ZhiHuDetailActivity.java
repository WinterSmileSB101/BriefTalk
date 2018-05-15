package com.alvin.smilesb101.brieftalk.View.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.alvin.smilesb101.brieftalk.Bean.ZhiHuContentInsideBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuContentOutSideBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuStory;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuTopStory;
import com.alvin.smilesb101.brieftalk.Presenter.ZhiHuNewsPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.BaseActivity.ThemeBaseActivity;
import com.alvin.smilesb101.brieftalk.View.Interface.Activity.IZhiHuDetailsView;
import com.alvin.smilesb101.brieftalk.View.Utils.GlideBlurformation;
import com.alvin.smilesb101.brieftalk.databinding.ActivityZhiHuDetailBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ZhiHuDetailActivity extends ThemeBaseActivity implements IZhiHuDetailsView {


    private String TAG = "ZhiHuDetailsActivity";
    ActivityZhiHuDetailBinding binding;
    public static final String Item_TOP_KEY = "item_top";
    public static final String Item_KEY = "item";
    String id;

    private ZhiHuTopStory topStory;
    private ZhiHuStory story;

    private ZhiHuNewsPresenter presenter;

    ImageView imageView;
    WebView html;
    CollapsingToolbarLayout collpasingToolBar;
    ProgressBar progressBar;

    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_zhi_hu_detail);

        rootView = binding.getRoot();
        imageView = (ImageView)rootView.findViewById(R.id.imageView);
        html = (WebView)rootView.findViewById(R.id.html);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar1);

        collpasingToolBar = (CollapsingToolbarLayout)rootView.findViewById(R.id.collpasingToolBar);

        Intent intent = getIntent();
        if(intent!=null)
        {
            if(intent.getSerializableExtra(Item_TOP_KEY)!=null) {
                topStory = (ZhiHuTopStory) intent.getSerializableExtra(Item_TOP_KEY);
                id = topStory.id;
                /**
                 * 设置默认值，开启共享动画
                 */
                Glide.with(this)
                        .load(topStory.image)
                        .apply(bitmapTransform(new BlurTransformation(25, 3)))
                        .into(imageView);
                collpasingToolBar.setTitle(topStory.title);
            }
            else if(intent.getSerializableExtra(Item_KEY)!=null)
            {
                story = (ZhiHuStory) intent.getSerializableExtra(Item_KEY);
                id = story.id;
                /**
                 * 设置默认值，开启共享动画
                 */
                Glide.with(this)
                        .load(story.images.get(0))
                        .apply(bitmapTransform(new BlurTransformation(14, 3)))
                        .into(imageView);
                collpasingToolBar.setTitle(story.title);
            }

            if(!id.equals(""))
            {
                //Log.i(TAG,"onCreate: 获取到ID:"+bean.getId());
                presenter = new ZhiHuNewsPresenter(this);
                presenter.getContentById();
            }
        }

        Toolbar toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void showContent(ZhiHuContentInsideBean insideBean) {
        try {
            //内部的展示
            //html.loadData(insideBean.getBody(), "text/html; charset=UTF-8", null);//这样写才能解决乱码问题，官方提供的写法解决不了中文乱码

            String s = "<link rel=\"stylesheet\" href=\""+insideBean.getCss()[0]+"\" type=\"text/css\" />"+insideBean.getBody();//引入Css
            s = s.replace("img-place-holder","");//去掉头部的白框
            html.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                    progressBar.setProgress(0);
                    view.loadUrl(request.getUrl().toString());
                    return true;
                }
            });
//			html.getSettings().setUseWideViewPort(true);//设置webview推荐使用的窗口，设置为true
//			html.getSettings().setLoadWithOverviewMode(true);//设置webview加载的页面的模式，也设置为true
            html.getSettings().setJavaScriptEnabled(true);
            html.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view,int newProgress){
                    //更新进度条
                    if(progressBar.getProgress()==100)
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(newProgress);
                    }
                }
            });
            html.loadData(s, "text/html; charset=UTF-8", null);//载入网页
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showContent(ZhiHuContentOutSideBean outSideBean) {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onBackPressed(){
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
}
