package com.alvin.smilesb101.brieftalk.View.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.alvin.smilesb101.brieftalk.Bean.BaiDuTranslateBean;
import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean.Word;
import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean._User;
import com.alvin.smilesb101.brieftalk.Bean.YouDaoTranslateDataBean;
import com.alvin.smilesb101.brieftalk.Presenter.KingSoftPresenter;
import com.alvin.smilesb101.brieftalk.Presenter.YouDaoTranslatePresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.BaseActivity.ThemeBaseActivity;
import com.alvin.smilesb101.brieftalk.View.Adapter.CardLearnPageAdapter;
import com.alvin.smilesb101.brieftalk.View.Fragment.CardLearnFragment;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.ITranslateFrament;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.ActivityReviewBinding;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class ReviewActivity extends ThemeBaseActivity implements ITranslateFrament,View.OnClickListener {

    static final String TAG = ReviewActivity.class.getSimpleName();

    List<Word> words;
    YouDaoTranslateDataBean currentTrans;

    KingSoftPresenter presenter;

    Context context;

    ActivityReviewBinding binding;

    int reNum,notReNum,totalNum,pos;

    private YouDaoTranslatePresenter youDaoTranslatePresenter;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_review);
        context = this;

        binding.setTitleText("复习检测");

        binding.ukAudioBtn.setVisibility(View.INVISIBLE);
        binding.usAudioBtn.setVisibility(View.INVISIBLE);
        binding.means.setVisibility(View.GONE);
        binding.notReBtn.setOnClickListener(this);
        binding.showMeans.setOnClickListener(this);
        binding.ukAudioBtn.setOnClickListener(this);
        binding.reBtn.setOnClickListener(this);
        binding.usAudioBtn.setOnClickListener(this);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        youDaoTranslatePresenter = new YouDaoTranslatePresenter(this);
        getWrods();
    }

    void getWrods(){
        BmobQuery<Word> wordBmobQuery = new BmobQuery<>();
        _User user = BmobUser.getCurrentUser(this,_User.class);
        if(user!=null) {
            wordBmobQuery.addWhereEqualTo("userId",user.userId);
            wordBmobQuery.addWhereEqualTo("review_plan",true);
            wordBmobQuery.findObjects(this, new FindListener<Word>() {
                @Override
                public void onSuccess(List<Word> list) {
                    Collections.sort(list);//排序
                    words = list;
                    totalNum = words.size();
                    reNum = words.size();
                    notReNum = words.size();
                    binding.rember.setText(reNum+"");
                    binding.notRember.setText(notReNum+"");
                    pos=0;
                    showWords(pos);
                }

                @Override
                public void onError(int i, String s) {
                    ToastUtils.show(context,s);
                }
            });
        }
    }

    private void showWords(int pos)
    {
        binding.ukAudioBtn.setVisibility(View.INVISIBLE);
        binding.usAudioBtn.setVisibility(View.INVISIBLE);
        binding.means.setVisibility(View.GONE);
        binding.showMeans.setVisibility(View.VISIBLE);

        Word word = words.get(pos);
        binding.word.setText(word.getWord());

        String lanto = word.getWordFrom()=="en"?"中文":"英文";
        youDaoTranslatePresenter.translateWordOnline("自动",lanto,word.getWord(),"BriefTalk");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showError(String error) {
        ToastUtils.show(context,"服务器被黑洞吃掉了额.\n"+error);
    }

    @Override
    public void showTranslateResult(BaiDuTranslateBean translateBean) {

    }

    @Override
    public void showYouDaoTranslateOnline(YouDaoTranslateDataBean translateDataBean) {
        currentTrans = translateDataBean;
        binding.wordUk.setText(translateDataBean.getTranslate().getUkPhonetic());
        binding.wordUS.setText(translateDataBean.getTranslate().getUsPhonetic());

        if(translateDataBean.getTranslate().getUKSpeakUrl()!=null
                &&translateDataBean.getTranslate().getUkPhonetic()!=null
                &&!translateDataBean.getTranslate().getUkPhonetic().isEmpty())
        {
            binding.ukAudioBtn.setVisibility(View.VISIBLE);
        }

        if(translateDataBean.getTranslate().getUSSpeakUrl()!=null
    &&translateDataBean.getTranslate().getUsPhonetic()!=null
                &&!translateDataBean.getTranslate().getUsPhonetic().isEmpty())
        {
            binding.usAudioBtn.setVisibility(View.VISIBLE);
        }
    }

    private void playAudio(String url)
    {
        //播放音频
        final MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try{
            mediaPlayer.setDataSource(context, Uri.parse(url));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            ToastUtils.show(context,"播放的时候遇到一些小问题。。。\n"+e.getMessage());
        }
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.notReBtn:
                //不记得
                notReNum--;
                if(notReNum>0)
                {
                    binding.notRember.setText(notReNum+"");
                }
                else{
                    binding.notRember.setText("0");
                }
                pos++;
                if(pos>=words.size())
                {
                    ToastUtils.show(context,"恭喜你，你已经完成今日任务拉.");
                }
                else {
                    showWords(pos);
                }
                break;

            case R.id.reBtn:
                //记得
                reNum--;
                if(reNum>0)
                {
                    binding.rember.setText(reNum+"");
                }
                else{
                    binding.rember.setText("0");
                }
                pos++;
                if(pos>=words.size())
                {
                    ToastUtils.show(context,"恭喜你，你已经完成今日任务拉.");
                }
                else {
                    showWords(pos);
                }
                break;
            case R.id.usAudioBtn:
                //美式发音
                if(binding.usAudioBtn.getVisibility()==View.VISIBLE)
                {
                    playAudio(currentTrans.getTranslate().getUSSpeakUrl());
                }
                break;
            case R.id.ukAudioBtn:
                //英式发音
                if(binding.ukAudioBtn.getVisibility()==View.VISIBLE)
                {
                    playAudio(currentTrans.getTranslate().getUKSpeakUrl());
                }
                break;
            case R.id.showMeans:
                //显示释义按钮
                if(currentTrans!=null)
                {
                    binding.means.setText(currentTrans.getTranslate().getTranslations().toString());
                    binding.showMeans.setVisibility(View.GONE);
                    binding.means.setVisibility(View.VISIBLE);
                }
                else{
                    ToastUtils.show(context,"没有任何释义噢.");
                }
                break;
        }
    }
}
