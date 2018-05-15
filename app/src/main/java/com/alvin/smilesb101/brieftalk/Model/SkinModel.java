package com.alvin.smilesb101.brieftalk.Model;

import android.graphics.Color;

import com.alvin.smilesb101.brieftalk.Bean.SkinBean;

import java.util.ArrayList;

public class SkinModel {
    public static final SkinModel SKIN_MODEL = new SkinModel();

    public interface getSkinCallBack{
        void onSucess(ArrayList<SkinBean> skinBeanArrayList);
        void onError(String error);
    }

    public void getSkinList(getSkinCallBack callBack)
    {
        ArrayList<SkinBean> arrayList = new ArrayList<>();
        arrayList.add(new SkinBean("活力红","", Color.RED,"",true));
        arrayList.add(new SkinBean("地域黑","",Color.BLACK,"",false));
        arrayList.add(new SkinBean("原谅绿","",Color.GREEN,"",false));
        callBack.onSucess(arrayList);
    }

}
