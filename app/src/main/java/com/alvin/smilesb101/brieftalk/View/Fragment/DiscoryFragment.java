package com.alvin.smilesb101.brieftalk.View.Fragment;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Adapter.DiscoryPageAdapter;
import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;
import com.alvin.smilesb101.brieftalk.databinding.FragmentDiscoryBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoryFragment extends FragmentBase {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentDiscoryBinding binding;
    DiscoryPageAdapter adapter;

    ArrayList<FragmentBase> fragmentBases;

    public DiscoryFragment() {
        // Required empty public constructor
        title = "发现";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoryFragment newInstance(String param1, String param2) {
        DiscoryFragment fragment = new DiscoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static DiscoryFragment newInstance(Bundle bundle) {
        DiscoryFragment fragment = new DiscoryFragment();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_discory, container, false);
        rootView = binding.getRoot();
        bindValue();
        initView();
        initValue();
        return rootView;
    }

    private void bindValue() {
        fragmentBases = new ArrayList<>();
        fragmentBases.add(laiFuDaoFragment.newInstance(null));
        fragmentBases.add(LaiFuDaoImageFragment.newInstance(null));
        fragmentBases.add(ShenHuiFuFragment.newInstance(null));

        for(int i = 0;i<fragmentBases.size();i++)
        {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(fragmentBases.get(0).getTitle()));
        }

        adapter = new DiscoryPageAdapter(this.getFragmentManager(),fragmentBases);

        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private void initView() {
    }

    private void initValue() {
    }

}
