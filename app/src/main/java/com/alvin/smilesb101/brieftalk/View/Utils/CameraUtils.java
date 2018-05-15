package com.alvin.smilesb101.brieftalk.View.Utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.alvin.smilesb101.brieftalk.View.Fragment.BaseFragment.FragmentBase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraUtils {
    /**
     * 获取调用系统相机拍照的 Intent
     * @param fragmentBase
     * @param view
     * @param filePath
     */
    public static void takePhoto(FragmentBase fragmentBase, View view, String filePath){
        String state = Environment.getExternalStorageState();//判断是否有 SD 卡
        if(state.equals(Environment.MEDIA_MOUNTED)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            Uri uri = null;
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                uri = FileProvider.getUriForFile(fragmentBase.getContext(),"com.alvin.smilesb101.brieftalk.fileprovider",new File(filePath));
            }
            else {
                uri = Uri.fromFile(new File(filePath));
            }

            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            fragmentBase.startActivityForResult(intent,1);
        }
        else{
            ToastUtils.show(fragmentBase.getContext(),"找了半天没有发现 SD 卡呢。");
        }
    }

    /**
     * 生成拍照的文件路径和文件名称
     * @return 文件全路径
     */
    public static String getPicName(String path){
        String saveDir = path;
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
