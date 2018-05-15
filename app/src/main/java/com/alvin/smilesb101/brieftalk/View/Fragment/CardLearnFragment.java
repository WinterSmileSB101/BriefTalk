package com.alvin.smilesb101.brieftalk.View.Fragment;


import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;

import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean.Word;
import com.alvin.smilesb101.brieftalk.Bean.KingSoftWordBean;
import com.alvin.smilesb101.brieftalk.Bean.KingSoftWordPartsBen;
import com.alvin.smilesb101.brieftalk.Presenter.KingSoftPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.IWordBookView;
import com.alvin.smilesb101.brieftalk.databinding.FragmentCardLearnBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardLearnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardLearnFragment extends FragmentBase implements IWordBookView{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Word word;
    KingSoftPresenter presenter;

    FragmentCardLearnBinding binding;

    View rootPanel;

    private ScaleAnimation sato0 = new ScaleAnimation(1,0,1,1,
            Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);

    private ScaleAnimation sato1 = new ScaleAnimation(0,1,1,1,
            Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);

    public CardLearnFragment() {
        // Required empty public constructor
        title="卡片查词";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardLearnFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardLearnFragment newInstance(String param1, String param2) {
        CardLearnFragment fragment = new CardLearnFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static CardLearnFragment newInstance(Bundle bundle) {
        CardLearnFragment fragment = new CardLearnFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static CardLearnFragment newInstance(Word word) {
        CardLearnFragment fragment = new CardLearnFragment();
        fragment.word = word;
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_card_learn, container, false);
        rootView = binding.getRoot();
        BindingValue();
        return rootView;
    }

    private void BindingValue() {
        sato0.setDuration(500);
        sato1.setDuration(500);

        sato0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (binding.srcPanel.getVisibility() == View.VISIBLE){
                    rootPanel.setAnimation(null);
                    showMeanPanel();
                    rootPanel.startAnimation(sato1);
                }else{
                    rootPanel.setAnimation(null);
                    showSrcPanel();
                    rootPanel.startAnimation(sato1);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        binding.srcText.setText(word.getWord());
        presenter = new KingSoftPresenter(this);
        presenter.getTranslate(word.getWord(),0);
        rootPanel = rootView.findViewById(R.id.rootPanel);
        rootPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootPanel.startAnimation(sato0);
            }
        });
    }

    private void showSrcPanel(){
        binding.srcPanel.setVisibility(View.VISIBLE);
        binding.meanPanel.setVisibility(View.GONE);
    }
    private void showMeanPanel(){

        binding.srcPanel.setVisibility(View.GONE);
        binding.meanPanel.setVisibility(View.VISIBLE);
    }

    @Override
    public void showWordTranslate(KingSoftWordBean wordBean, int pos) {
        ArrayList<KingSoftWordPartsBen> partsBens = wordBean.getSymbols().get(0).getParts();

        String binText = "";
        for (KingSoftWordPartsBen partsBen:partsBens) {
            binText+=partsBen.getPart()+partsBen.getMeans().toString()+"<br/>";
        }
        if(wordBean.getSymbols().get(0).getPg_en_mp3()!=null)
        {
            String word = wordBean.getSymbols().get(0).getPh_en();
            binding.textUs.setText("美式 "+word);
            binding.usPanel.setVisibility(View.VISIBLE);
        }

        if(wordBean.getSymbols().get(0).getPh_am_mp3()!=null){
            binding.textUk.setText("英式 "+wordBean.getSymbols().get(0).getPh_am());
            String word = wordBean.getSymbols().get(0).getPh_am();
            binding.ukPanel.setVisibility(View.VISIBLE);
        }
        binding.menText.setText(Html.fromHtml(binText));
    }

    @Override
    public void showWord(Word word) {

    }

    @Override
    public void onError(String error) {

    }
}
