package com.alvin.smilesb101.brieftalk.View.Fragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvin.smilesb101.brieftalk.Bean.BaiDuTranslateBean;
import com.alvin.smilesb101.brieftalk.Bean.YouDaoTranslateDataBean;
import com.alvin.smilesb101.brieftalk.Presenter.BaiduTranslatePresenter;
import com.alvin.smilesb101.brieftalk.Presenter.YouDaoTranslatePresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.FullscreenActivity;
import com.alvin.smilesb101.brieftalk.View.Adapter.MoreTranslationRecyclerAdapter;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.ITranslateFrament;
import com.alvin.smilesb101.brieftalk.View.Utils.Helper;
import com.alvin.smilesb101.brieftalk.View.Utils.Share;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.FragmentTranslateBinding;

import java.io.IOException;

import static android.content.Context.CLIPBOARD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TranslateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TranslateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TranslateFragment extends FragmentBase implements ITranslateFrament,TextWatcher,View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private FragmentTranslateBinding binding;
    private BaiduTranslatePresenter translatePresenter;
    private YouDaoTranslatePresenter youDaoTranslatePresenter;
    private MoreTranslationRecyclerAdapter moreTranslationAdatper;
    private boolean searchAble = true;

    private YouDaoTranslateDataBean translateDataBean;

    private Handler handler = new Handler();

    public TranslateFragment() {
        // Required empty public constructor
        title = "翻译";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TranslateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TranslateFragment newInstance(String param1, String param2) {
        TranslateFragment fragment = new TranslateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static TranslateFragment newInstance(Bundle bundle) {
        TranslateFragment fragment = new TranslateFragment();
        if(bundle!=null) {
            fragment.setArguments(bundle);
        }
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_translate,container,false);
        rootView = binding.getRoot();
        rootContext = binding.getRoot().getContext();
        bindingValue();
        initView();
        initValue();
        return rootView;
    }

    void bindingValue(){

    }

    void initView(){
        setSearchView(false);
        binding.translateInput.addTextChangedListener(this);
        LinearLayoutManager layout = new LinearLayoutManager(this.rootContext,LinearLayoutManager.VERTICAL,false);
        binding.moreTranstion.setLayoutManager(layout);
        binding.moreTranstion.setNestedScrollingEnabled(false);

        binding.getRoot().findViewById(R.id.audio).setOnClickListener(this);
        binding.getRoot().findViewById(R.id.share).setOnClickListener(this);
        binding.getRoot().findViewById(R.id.copy).setOnClickListener(this);
        binding.getRoot().findViewById(R.id.fullscreen).setOnClickListener(this);
    }

    void initValue(){
        translatePresenter = new BaiduTranslatePresenter(this);
        youDaoTranslatePresenter = new YouDaoTranslatePresenter(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showTranslateResult(BaiDuTranslateBean translateBean) {
        Log.i(TAG, "showTranslateResult: "+translateBean.trans_result.get(0).dst);
    }

    @Override
    public void showYouDaoTranslateOnline(YouDaoTranslateDataBean translateDataBean) {
        Log.i(TAG, "showYouDaoTranslateOnline: "+translateDataBean.getTranslate().getTranslations().size());
        //binding.translateResultPanel.setVisibility(View.VISIBLE);
        //binding.translateHistory.setVisibility(View.GONE);
        this.translateDataBean = translateDataBean;
        binding.setTranslationBean(translateDataBean);

        if(translateDataBean.getTranslate().getWebExplains()!=null) {
            moreTranslationAdatper = new MoreTranslationRecyclerAdapter(translateDataBean.getTranslate(), this, binding.moreTranstion);
            binding.moreTranstion.setAdapter(moreTranslationAdatper);
        }
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i(TAG, "onTextChanged: "+s.toString());
        if(TextUtils.isEmpty(binding.translateInput.getText().toString().trim())){
            setSearchView(false);
        }
        else
        {
            setSearchView(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @SuppressLint("ResourceAsColor")
    public void setSearchView(boolean searchable){
        if(this.searchAble != searchable) {
            this.searchAble = searchable;
            if (this.searchAble) {
                binding.translateInputPanel.setOnClickListener(this);
                binding.translateText.setTextColor(R.color.colorAccent);
                binding.translateImage.setImageDrawable(Helper.setVectorDrawableColor(R.color.colorRed, R.drawable.ic_right_arrow, this.getActivity()));
                binding.translateClearBtn.setVisibility(View.VISIBLE);
            } else {
                binding.translateInputPanel.setOnClickListener(null);
                binding.translateText.setTextColor(R.color.black_overlay);
                binding.translateImage.setImageDrawable(Helper.setVectorDrawableColor(R.color.black_overlay, R.drawable.ic_right_arrow, this.getActivity()));
                binding.translateClearBtn.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.translateInputPanel:
                String input = binding.translateInput.getText().toString().trim();
                translatePresenter.translateWord(input);
                youDaoTranslatePresenter.translateWordOnline("自动","英文",input,"BriefTalk");
                break;
            case R.id.audio:
                //播放音频
                final MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try{
                    Log.i(TAG, "onClick: "+translateDataBean.getTranslate().getSpeakUrl());
                    mediaPlayer.setDataSource(rootView.getContext(),Uri.parse(translateDataBean.getTranslate().getSpeakUrl()));
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });
                } catch (IOException e) {
                    ToastUtils.show(rootContext,"播放的时候遇到一些小问题。。。\n"+e.getMessage());
                }
                break;
            case R.id.copy:
                //复制
                try {
                    ClipboardManager cm = (ClipboardManager) rootContext.getSystemService(CLIPBOARD_SERVICE);
                    //cm.setText(translateDataBean.getTranslate().getTranslations().get(0));
                    cm.setPrimaryClip(ClipData.newPlainText("word",translateDataBean.getTranslate().getTranslations().get(0)));
                    ToastUtils.show(rootContext,"成功复制到剪贴板！");
                }
                catch (NullPointerException ne)
                {
                    ToastUtils.show(rootContext,"复制失败了。\n"+ne.getMessage());
                }
                break;
            case R.id.share:
                Share share = new Share(rootContext);
                share.shareToQQ(TranslateFragment.this.getActivity(),"来自曦语的分享","原文："+translateDataBean.getTranslate().getQuery()+"，译文："+translateDataBean.getTranslate().getTranslations().get(0)+"\n@ 来自曦语的分享");
                break;
            case R.id.fullscreen:
                Intent intent = new Intent(this.getActivity(), FullscreenActivity.class);
                intent.putExtra("WordBean",translateDataBean);
                this.getActivity().startActivity(intent);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
