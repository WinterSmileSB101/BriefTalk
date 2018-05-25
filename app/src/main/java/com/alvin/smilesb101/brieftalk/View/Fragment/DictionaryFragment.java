package com.alvin.smilesb101.brieftalk.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvin.smilesb101.brieftalk.Bean.OcrReconizeBean;
import com.alvin.smilesb101.brieftalk.Bean.TodayWordBean;
import com.alvin.smilesb101.brieftalk.Bean.YouDaoTranslateDataBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuLastNewsBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuNewsExtraBean;
import com.alvin.smilesb101.brieftalk.Bean.ZhiHuStory;
import com.alvin.smilesb101.brieftalk.Presenter.KingSoftTodayWordPresenter;
import com.alvin.smilesb101.brieftalk.Presenter.OcrTranslatePresenter;
import com.alvin.smilesb101.brieftalk.Presenter.ZhiHuNewsPresenter;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.MainActivity;
import com.alvin.smilesb101.brieftalk.View.Activity.ShowOcrActivity;
import com.alvin.smilesb101.brieftalk.View.Adapter.ZhiHuNewsRecyclerAdapter;
import com.alvin.smilesb101.brieftalk.View.CustomView.CustomLinearLayoutManager;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.IDictionaryFragment;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.IOcrTranslateFragment;
import com.alvin.smilesb101.brieftalk.View.Utils.CameraUtils;
import com.alvin.smilesb101.brieftalk.View.Utils.GlideImageLoader;
import com.alvin.smilesb101.brieftalk.View.Utils.Helper;
import com.alvin.smilesb101.brieftalk.View.Utils.TesserTwoUtils;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.FragmentDictionaryBinding;
import com.google.gson.Gson;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youdao.ocr.online.OCRParameters;
import com.youdao.ocr.online.RecognizeLanguage;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DictionaryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DictionaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DictionaryFragment extends FragmentBase implements View.OnClickListener,IDictionaryFragment,IOcrTranslateFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String TAG = DictionaryFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private FragmentDictionaryBinding binding;
    private KingSoftTodayWordPresenter presenter;
    private ZhiHuNewsPresenter zhiHuNewsPresenter;
    private OcrTranslatePresenter ocrTranslatePresenter;
    private Banner banner;
    private ZhiHuNewsRecyclerAdapter zhiHuNewsRecyclerAdapter;
    public MainActivity activity;

    private YouDaoOcrFragment ocrFragment;

    private ZhiHuLastNewsBean newsBean;
    private String filePath;
    private Handler handler = new Handler();
    private RefreshLayout refreshLayout;

    private OcrTranslateShowFragment showFragment;

    private TessBaseAPI tessBaseAPI = new TessBaseAPI();


    public DictionaryFragment() {
        // Required empty public constructor
        title = "词典";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DictionaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DictionaryFragment newInstance(String param1, String param2) {
        DictionaryFragment fragment = new DictionaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static DictionaryFragment newInstance(Bundle bundle) {
        DictionaryFragment fragment = new DictionaryFragment();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dictionary, container, false);
        Log.i(TAG, "onCreateView: "+binding);
        rootContext = binding.getRoot().getContext();
        rootView = binding.getRoot();
        bindValue();
        initView();
        initValue();
        return rootView;
    }

    void bindValue(){
        binding.setMFragment(this);
    }

    void initView(){
        banner = binding.banner;
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.setIndicatorGravity(BannerConfig.RIGHT);

        binding.getRoot().findViewById(R.id.translateBtn).setOnClickListener(this);

        refreshLayout = binding.getRoot().findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //刷新，
                //没有更多了哟
                zhiHuNewsPresenter.getLastNews();
                //refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ToastUtils.show(rootContext,"没有更多了哟");
                refreshlayout.finishLoadmore(/*,false*/);//传入false表示加载失败
            }
        });
        //binding.searchView.setQueryHint("请在此输入需要翻译的文本");
    }

    void initValue(){
        presenter = new KingSoftTodayWordPresenter(this);
        presenter.getBanner();//load Banner Data

        zhiHuNewsPresenter = new ZhiHuNewsPresenter(this);
        zhiHuNewsPresenter.getLastNews();//hot List

        ocrTranslatePresenter = new OcrTranslatePresenter(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.camera_translate:
                //开始拍照，search
                filePath= CameraUtils.getPicName(Environment.getExternalStorageDirectory()+"/BriefTalk/Ocr");
                CameraUtils.takePhoto(this,rootView,filePath);
                break;
            //case R.id.fab:
            case R.id.translateBtn:
                //Search 部分，search
                activity.selectFragment(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void onError(String error) {
        Log.i(TAG, "onError: "+error);
        banner.stopAutoPlay();
    }

    /**
     * Ocr 翻译结果
     * @param spannableString
     * @param input
     */
    @Override
    public void showOcrTranslate(SpannableString spannableString, String input) {
        Log.i(TAG, "showOcrTranslate: "+spannableString);
    }

    @Override
    public void showOcrTranslateOnline(YouDaoTranslateDataBean translateDataBean,String input) {
        Log.i(TAG, "showOcrTranslateOnline: "+translateDataBean.getTranslate().getTranslations().get(0));
        if(showFragment==null)
        {
            Bundle bundle = new Bundle();
            bundle.putString(OcrTranslateShowFragment.ARG_INPUT,input);
            bundle.putString(OcrTranslateShowFragment.ARG_TRANSLATION,translateDataBean.getTranslate().getTranslations().get(0));
            showFragment = OcrTranslateShowFragment.newInstance(bundle);
        }
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .addToBackStack(TAG)
                .replace(R.id.rootView,showFragment)
                .commit();
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void showBanner(ArrayList<String> images,ArrayList<String> titles) {

        Log.i(TAG, "showBanner: "+images.get(0));
        banner.update(images,titles);
        banner.startAutoPlay();
        banner.start();
    }

    @Override
    public void showHotList(ZhiHuLastNewsBean newsBean) {
        Log.i(TAG, "showHotList: "+newsBean.stories.size());
        this.newsBean = newsBean;
        getStoryNewsExtra();
        LinearLayoutManager layout = new LinearLayoutManager(this.rootContext,LinearLayoutManager.VERTICAL,false);

        //CustomLinearLayoutManager layout = new CustomLinearLayoutManager(this.rootContext);
        //layout.setScrollEnabled(false);
        binding.newsList.setLayoutManager(layout);
        //binding.newsList.addItemDecoration(new DividerItemDecoration(this.rootContext, LinearLayoutManager.VERTICAL));
        zhiHuNewsRecyclerAdapter = new ZhiHuNewsRecyclerAdapter(this.newsBean,this,binding.newsList);
        binding.newsList.setAdapter(zhiHuNewsRecyclerAdapter);

        refreshLayout.finishRefresh(/*,false*/);//传入false表示刷新失败
    }

    void getStoryNewsExtra(){
        for (int i=0;i<newsBean.stories.size();i++) {
            zhiHuNewsPresenter.getNewsExtra(newsBean.stories.get(i).id,i);//get extra
        }
    }

    @Override
    public void addNewsExtra(String id, int pos,ZhiHuNewsExtraBean extraBean) {
        if(id!=null&&id.trim()!=""){
            if(newsBean!=null)
            {
                newsBean.stories.get(pos).extra = extraBean;
                zhiHuNewsRecyclerAdapter.notifyItemChanged(pos);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: 自活动"+resultCode);
        if (resultCode == RESULT_OK) {
            Uri uri = null;
            Log.i(TAG, "onActivityResult: 自活动正确"+resultCode);
            if (data != null) {
                uri = data.getData();
            }
            //预防部分手机无法直接读取相机返回
            if (uri == null && !TextUtils.isEmpty(filePath)) {
                uri = Uri.parse(filePath);
            }
            if (uri == null) {
                return;
            }
            Log.i(TAG, "onActivityResult: "+uri);
            Bitmap bm = Helper.getBitmapFromSdCard(rootContext,uri.getPath());
            bm = Helper.convertGray(bm);
            //bm = Helper.gray2Binary(bm);
            TesserTwoUtils.newInstance(rootContext,TesserTwoUtils.ENG);
            String result = TesserTwoUtils.reconizePic(bm);
            Log.i(TAG, "onActivityResult: "+result);

            OcrReconizeBean bean = new OcrReconizeBean();
            bean.setUri(uri);
            Log.i(TAG, "onActivityResult: 开始翻译");
            //开始 ocr 翻译
            //ocrTranslatePresenter.ocrTranslate(bean,"BriefTalk", OCRParameters.TYPE_LINE, RecognizeLanguage.LINE_CHINESE_ENGLISH.getCode());
            Intent intent = new Intent();
            intent.setClass(this.getActivity(), ShowOcrActivity.class);
            intent.putExtra("input",bean);
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
