package com.alvin.smilesb101.brieftalk.View.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.googlecode.tesseract.android.TessBaseAPI;

public class TesserTwoUtils {
    private static String LANGUAGE_PATH = "";
    private static String LANGUAGE = "chi_sim";//简体中文

    private static TessBaseAPI tessBaseAPI = new TessBaseAPI();
    private static Context context;

    public static final String ENG = "eng";
    public static final String CHI_SIM = "chi_sim";

    /**
     * 设置 TesserTwoUtils（Tesser Two）
     * @param context
     */
    public static void newInstance(Context context,String lan){
        TesserTwoUtils.context = context;
        TesserTwoUtils.LANGUAGE = lan;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            LANGUAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/BriefTalk/";

            tessBaseAPI.init(LANGUAGE_PATH,LANGUAGE);

            tessBaseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
        }
    }

    public static String reconizePic(Bitmap bm){
        tessBaseAPI.setImage(bm);
        String result = tessBaseAPI.getUTF8Text();
        tessBaseAPI.end();
        return result;
    }

}
