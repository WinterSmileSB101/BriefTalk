package com.alvin.smilesb101.brieftalk.View.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class Share {

    private Context context;

    public static int TEXT =0;

    public static int DRAWABLE = 1;

    public Share(Context context) {
        this.context = context;
    }

    public void shareQQFriend(String msgTitle, String msgText, int type, Bitmap bitmap)
    {
        shareMsg("com.tencent.mobileqq","com.tencent.mobileqq.activity.JumpActivity","QQ",msgTitle,msgText,type,bitmap);
    }

    @SuppressLint("NewApi")
    private void shareMsg(String packageName,String activityName,String appName,String msgTitle,String msgText,int type,Bitmap bitmap)
    {
        if(!packageName.isEmpty()&&!isAvilible(context,packageName))
        {
            ToastUtils.show(context,"请先安装 "+ appName);
            return;
        }

        Intent intent = new Intent("android.intent.action.SEND");
        if (type == Share.TEXT) {
            intent.setType("text/plain");
        } else if (type == Share.DRAWABLE) {
            intent.setType("image/*");
//          BitmapDrawable bd = (BitmapDrawable) drawable;
//          Bitmap bt = bd.getBitmap();
            final Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(
                    context.getContentResolver(), bitmap, null, null));
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        }

        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!packageName.isEmpty()) {
            intent.setComponent(new ComponentName(packageName, activityName));
            context.startActivity(intent);
        } else {
            context.startActivity(Intent.createChooser(intent, msgTitle));
        }
    }

    public boolean isAvilible(Context context,String packageName)
    {
        PackageManager pm = context.getPackageManager();

        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);

        for (PackageInfo pi:packageInfos) {
            if(pi.packageName.equalsIgnoreCase(packageName))
            {
                return true;
            }
        }
        return false;
    }


    public void shareToQQ(Activity context,String title,String text)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setPackage("com.tencent.mobileqq");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE,title);
        intent.putExtra(Intent.EXTRA_TEXT,text);
        context.startActivity(intent);
    }
}
