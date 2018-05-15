package com.alvin.smilesb101.brieftalk.View.Fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean.Word;
import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean._User;
import com.alvin.smilesb101.brieftalk.Bean.KingSoftWordBean;
import com.alvin.smilesb101.brieftalk.Bean.KingSoftWordPartsBen;
import com.alvin.smilesb101.brieftalk.Presenter.KingSoftPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.CardLearnActivity;
import com.alvin.smilesb101.brieftalk.View.Activity.ZhiHuDetailActivity;
import com.alvin.smilesb101.brieftalk.View.Adapter.WordBookRecyclerAdapter;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.IWordBookView;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.FragmentWordBookBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordBookFragment extends FragmentBase implements IWordBookView,View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentWordBookBinding binding;
    WordBookRecyclerAdapter adapter;
    KingSoftPresenter presenter;

    ArrayList<Word> words;

    int reviewPlanWords;
    String planText = "共 33 词，30 词已经加入复习计划";
    TextView planInfo;
    TextView reviewWords;

    public WordBookFragment() {
        // Required empty public constructor
        title = "单词本";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WordBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WordBookFragment newInstance(String param1, String param2) {
        WordBookFragment fragment = new WordBookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static WordBookFragment newInstance(Bundle bundle) {
        WordBookFragment fragment = new WordBookFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_word_book, container, false);
        rootView = binding.getRoot();
        rootContext = rootView.getContext();
        bindValue();
        initView();
        initValue();
        return rootView;
    }

    private void bindValue() {
        planInfo = rootView.findViewById(R.id.planInfo);
        reviewWords = rootView.findViewById(R.id.reviewWords);

        rootView.findViewById(R.id.cardLeaningBtn).setOnClickListener(this);
        rootView.findViewById(R.id.reviewBtn).setOnClickListener(this);

        LinearLayoutManager layout = new LinearLayoutManager(this.rootContext,LinearLayoutManager.VERTICAL,false);
        binding.wordRecycler.setLayoutManager(layout);
        presenter = new KingSoftPresenter(this);
        getWrods();
    }

    void getWrods(){
        BmobQuery<Word> wordBmobQuery = new BmobQuery<>();
        _User user = BmobUser.getCurrentUser(this.getContext(),_User.class);
        if(user!=null) {
            wordBmobQuery.addWhereEqualTo("userId",user.userId);
            wordBmobQuery.findObjects(this.getContext(), new FindListener<Word>() {
                @Override
                public void onSuccess(List<Word> list) {
                    words = (ArrayList<Word>) list;
                    Collections.sort(words);//排序
                    getWordsCount();
                    adapter = new WordBookRecyclerAdapter(words,WordBookFragment.this,binding.wordRecycler);
                    binding.wordRecycler.setAdapter(adapter);
                    for (int i = 0;i<list.size();i++)
                    {
                        Log.i(TAG, "onSuccess: "+list.get(i).getWord());
                        presenter.getTranslate(list.get(i).getWord(),i);
                    }
                }

                @Override
                public void onError(int i, String s) {
                    ToastUtils.show(WordBookFragment.this.getContext(),s);
                }
            });
        }
    }

    void getWordsCount(){
        int totalCount = words.size();
        int reviewCount = 0;
        for (Word word:words) {
            if(word.isReview_plan())
            {
                reviewCount++;
            }
        }
        reviewPlanWords = reviewCount;
        planText = "共 "+totalCount+" 词，"+reviewPlanWords+" 词已经加入复习计划";
        planInfo.setText(planText);
        if(reviewPlanWords>0) {
            reviewWords.setText(reviewPlanWords + "");
        }
        else{
            reviewWords.setText("");
        }
    }

    private void initView() {
    }

    private void initValue() {
    }

    @Override
    public void showWordTranslate(KingSoftWordBean wordBean,int pos) {
        Log.i(TAG, "showWordTranslate: 获取到翻译"+wordBean);
        String parts = "";
        for (KingSoftWordPartsBen part:wordBean.getSymbols().get(0).getParts()) {
            parts+=part.getPart()+part.getMeans().toString()+"  ";
        }
        words.get(pos).setParts(parts);
        adapter.notifyItemChanged(pos);
    }

    @Override
    public void showWord(Word word) {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cardLeaningBtn:
                //去卡片学习
                Intent intent = new Intent(rootContext,CardLearnActivity.class);//新建Intent

                rootContext.startActivity(intent, ActivityOptionsCompat.makeBasic().toBundle());//开新活动

                break;
            case R.id.reviewBtn:
                break;
        }
    }
}
