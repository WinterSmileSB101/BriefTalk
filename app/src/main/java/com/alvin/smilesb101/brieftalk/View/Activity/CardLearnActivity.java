package com.alvin.smilesb101.brieftalk.View.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean.Word;
import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean._User;
import com.alvin.smilesb101.brieftalk.Bean.KingSoftWordBean;
import com.alvin.smilesb101.brieftalk.Presenter.KingSoftPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.BaseActivity.ThemeBaseActivity;
import com.alvin.smilesb101.brieftalk.View.Adapter.CardLearnPageAdapter;
import com.alvin.smilesb101.brieftalk.View.Adapter.WordBookRecyclerAdapter;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.View.Fragment.CardLearnFragment;
import com.alvin.smilesb101.brieftalk.View.Fragment.WordBookFragment;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.IWordBookView;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.ActivityCardLearnBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class CardLearnActivity extends ThemeBaseActivity {

    static final String TAG = CardLearnActivity.class.getSimpleName();

    ArrayList<FragmentBase> fragmentBases;

    KingSoftPresenter presenter;

    Context context;

    CardLearnPageAdapter adapter;

    ActivityCardLearnBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_card_learn);
        context = binding.getRoot().getContext();
        fragmentBases = new ArrayList<>();
        binding.setTitleText("卡片学习");
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWrods();
    }


    void getWrods(){
        BmobQuery<Word> wordBmobQuery = new BmobQuery<>();
        _User user = BmobUser.getCurrentUser(this,_User.class);
        if(user!=null) {
            wordBmobQuery.addWhereEqualTo("userId",user.userId);
            wordBmobQuery.findObjects(this, new FindListener<Word>() {
                @Override
                public void onSuccess(List<Word> list) {
                    Collections.sort(list);//排序
                    for (int i = 0;i<list.size();i++)
                    {
                        fragmentBases.add(CardLearnFragment.newInstance(list.get(i)));
                    }
                    adapter = new CardLearnPageAdapter(CardLearnActivity.this.getSupportFragmentManager(),fragmentBases);

                    binding.viewPager.setPageMargin(80);
                    binding.viewPager.setOffscreenPageLimit(3);
                    binding.viewPager.setAdapter(adapter);
                }

                @Override
                public void onError(int i, String s) {
                    ToastUtils.show(context,s);
                }
            });
        }
    }

}
