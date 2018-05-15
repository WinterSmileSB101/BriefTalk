package com.alvin.smilesb101.brieftalk.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvin.smilesb101.brieftalk.Bean.OcrReconizeBean;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Adapter.OcrRecyclerAdapter;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.FragmentYouDaoOcrBinding;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YouDaoOcrFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YouDaoOcrFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YouDaoOcrFragment extends FragmentBase {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private FragmentYouDaoOcrBinding binding;
    private String filePath;
    private ArrayList<OcrReconizeBean> ocrReconizeBeans;

    private OcrRecyclerAdapter ocrRecyclerAdapter;

    public YouDaoOcrFragment() {
        // Required empty public constructor
        title = "拍照翻译";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YouDaoOcrFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YouDaoOcrFragment newInstance(String param1, String param2) {
        YouDaoOcrFragment fragment = new YouDaoOcrFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static YouDaoOcrFragment newInstance(Bundle bundle){
        YouDaoOcrFragment fragment = new YouDaoOcrFragment();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_you_dao_ocr, container, false);
        rootView = binding.getRoot();
        bindingValue();
        initView();
        initValue();
        return rootView;
    }

    private void bindingValue() {


    }

    private void initView() {
        LinearLayoutManager layout = new LinearLayoutManager(this.rootContext,LinearLayoutManager.VERTICAL,false);
        binding.ocrRecyclerView.setLayoutManager(layout);
    }



    private void initValue(){
        ocrReconizeBeans = new ArrayList<>();
        ocrRecyclerAdapter = new OcrRecyclerAdapter(ocrReconizeBeans,this,binding.ocrRecyclerView);
        binding.ocrRecyclerView.setAdapter(ocrRecyclerAdapter);
        takePhoto(rootView);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public void takePhoto(View view){
        String state = Environment.getExternalStorageState();//判断是否有 SD 卡
        if(state.equals(Environment.MEDIA_MOUNTED)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            filePath = getPicName();
            Uri uri = null;
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                uri = FileProvider.getUriForFile(rootContext,"com.alvin.smilesb101.brieftalk.fileprovider",new File(filePath));
            }
            else {
                uri = Uri.fromFile(new File(filePath));
            }

            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            this.startActivityForResult(intent,1);
        }
        else{
            ToastUtils.show(rootContext,"找了半天没有发现 SD 卡呢。");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = null;
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
            OcrReconizeBean bean = new OcrReconizeBean();
            bean.setUri(uri);
            ocrReconizeBeans.add(bean);
            ocrRecyclerAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 生成拍照的文件路径和文件名称
     * @return 文件全路径
     */
    private String getPicName(){
        String saveDir = Environment.getExternalStorageDirectory()+"/BriefTalk/Ocr";
        File dir = new File(saveDir);
        if(!dir.exists()){
            dir.mkdirs();//必须这个，使用 mkdir 无法创建
        }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String fileName = saveDir+"/"+formatter.format(date)+".png";
        return fileName;
    }
}
