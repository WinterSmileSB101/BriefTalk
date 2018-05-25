package com.alvin.smilesb101.brieftalk.View.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.alvin.smilesb101.brieftalk.Bean.OcrReconizeBean;
import com.alvin.smilesb101.brieftalk.Bean.YouDaoTranslateDataBean;
import com.alvin.smilesb101.brieftalk.Presenter.OcrTranslatePresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.BaseActivity.ThemeBaseActivity;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.IOcrTranslateFragment;
import com.alvin.smilesb101.brieftalk.databinding.ActivityShowOcrBinding;
import com.youdao.ocr.online.OCRParameters;
import com.youdao.ocr.online.RecognizeLanguage;

public class ShowOcrActivity extends ThemeBaseActivity implements IOcrTranslateFragment{

    ActivityShowOcrBinding binding;
    private static String TAG;
    private OcrTranslatePresenter translatePresenter;
    private OcrReconizeBean input;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_ocr);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TAG = ShowOcrActivity.class.getSimpleName();
        bindingValue();

        initView();
        initValue();
    }

    private void bindingValue() {

    }
    private void initView() {
        Intent intent = getIntent();
        if(intent!=null)
        {
            translatePresenter = new OcrTranslatePresenter(this);
            input = (OcrReconizeBean) intent.getParcelableExtra("input");
            translatePresenter.ocrTranslate(input,"BriefTalk", OCRParameters.TYPE_LINE, RecognizeLanguage.LINE_CHINESE_ENGLISH.getCode());
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initValue() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void showOcrTranslate(SpannableString spannableString, String input) {

    }

    @Override
    public void showOcrTranslateOnline(YouDaoTranslateDataBean translateDataBean, String input) {
        binding.setInput(input);
        binding.setTranslation(translateDataBean.getTranslate().getTranslations().get(0));
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
