package com.alvin.smilesb101.brieftalk.View.Fragment;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.databinding.FragmentOcrTranslateShowBinding;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OcrTranslateShowFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OcrTranslateShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OcrTranslateShowFragment extends FragmentBase {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_INPUT = "input";
    public static final String ARG_TRANSLATION = "translation";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String input;
    private String translation;

    private OnFragmentInteractionListener mListener;

    private FragmentOcrTranslateShowBinding binding;

    public OcrTranslateShowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OcrTranslateShowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OcrTranslateShowFragment newInstance(String param1, String param2) {
        OcrTranslateShowFragment fragment = new OcrTranslateShowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static OcrTranslateShowFragment newInstance(Bundle bundle){
        OcrTranslateShowFragment fragment = new OcrTranslateShowFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            input = getArguments().getString(ARG_INPUT);
            translation = getArguments().getString(ARG_TRANSLATION);
        }
    }

    private void bindingValue() {
        binding.setInput(input);
        binding.setTranslation(translation);
    }
    private void initView() {
    }
    private void initValue() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_ocr_translate_show,container,false);
        rootView = binding.getRoot();
        bindingValue();
        initView();
        initValue();
        return rootView;
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
}
