package com.alvin.smilesb101.brieftalk.View.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Environment;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.text.TextUtils;
import android.util.Log;

import com.alvin.smilesb101.brieftalk.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class Helper {

    static final String TAG = Helper.class.getSimpleName();

    // 首先初始化一个字符数组，用来存放每个16进制字符
    private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd','e', 'f' };

    public static String getCurrentDate(int dateDiff) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,dateDiff);
//获取系统的日期
//年
        int year = calendar.get(Calendar.YEAR);
//月
        int month = calendar.get(Calendar.MONTH) + 1;
//日
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return year + "-" + month + "-" + day;

    }

    public static String md5(String string) {
        if (string == null)
            return null;
        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = string.getBytes("utf-8");
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String byteArrayToHex(byte[] byteArray) {
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }

        // 字符数组组合成字符串返回
        return new String(resultCharArray);

    }

    // 判断一个字符是否是中文
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    // 判断一个字符串是否含有中文
    public static boolean isChinese(String str) {
        if (str == null)
            return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c))
                return true;// 有一个中文字符就返回
        }
        return false;
    }

    public static VectorDrawableCompat setVectorDrawableColor(int color, int pic,Activity activity){
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(activity.getResources(), pic, activity.getTheme());//你需要改变的颜色
        vectorDrawableCompat.setTint(activity.getResources().getColor(color));
        // YourImageView.setImageDrawable(vectorDrawableCompat)
        return vectorDrawableCompat;
    }


    /**
     * 移动 assets 文件夹里的内容到手机 sd 卡，方便调用
     * @param context
     * @param path
     * @throws IOException
     */
    public static void copyLanDataToPhoneDir(Context context, String path) throws IOException {
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/BriefTalk/";
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            String str[] = context.getAssets().list(path);
            if(str.length>0)
            {
                File lanfile = new File(sdPath + path);
                if(!lanfile.exists())
                {
                    lanfile.mkdirs();
                    for (String string : str) {
                        path = path + "/" + string;
                        copyLanDataToPhoneDir(context,path);
                        path = path.substring(0, path.lastIndexOf('/'));//回到原来的path
                    }
                }
            }
            else {//如果是文件
                InputStream is = context.getAssets().open(path);
                FileOutputStream fos = new FileOutputStream(new File(sdPath + path));
                byte[] buffer = new byte[1024];
                int count = 0;
                while (true) {
                    count++;
                    int len = is.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
            }
        }
        else
        {
            Log.i(TAG, "请确认有 SD 卡！");
        }
    }

    /**
     * 灰度化处理
     *
     * @param bitmap3
     * @return
     */
    public static Bitmap convertGray(Bitmap bitmap3) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        Paint paint = new Paint();
        paint.setColorFilter(filter);
        Bitmap result = Bitmap.createBitmap(bitmap3.getWidth(), bitmap3.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(bitmap3, 0, 0, paint);
        return result;
    }

    /**
     * 对图像进行线性灰度变化
     * @param image
     * @return
     */
    public static Bitmap lineGrey(Bitmap image)
    {
        //得到图像的宽度和长度
        int width = image.getWidth();
        int height = image.getHeight();
        //创建线性拉升灰度图像
        Bitmap linegray = null;
        linegray = image.copy(Bitmap.Config.ARGB_8888, true);
        //依次循环对图像的像素进行处理
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //得到每点的像素值
                int col = image.getPixel(i, j);
                int alpha = col & 0xFF000000;
                int red = (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue = (col & 0x000000FF);
                // 增加了图像的亮度
                red = (int) (1.1 * red + 30);
                green = (int) (1.1 * green + 30);
                blue = (int) (1.1 * blue + 30);
                //对图像像素越界进行处理
                if (red >= 255)
                {
                    red = 255;
                }

                if (green >= 255) {
                    green = 255;
                }

                if (blue >= 255) {
                    blue = 255;
                }
                // 新的ARGB
                int newColor = alpha | (red << 16) | (green << 8) | blue;
                //设置新图像的RGB值
                linegray.setPixel(i, j, newColor);
            }
        }
        return linegray;
    }


    /**
     * 对图像进行二值化处理
     * @param graymap
     * @return
     */
    public static Bitmap gray2Binary(Bitmap graymap) {
        //得到图形的宽度和长度
        int width = graymap.getWidth();
        int height = graymap.getHeight();
        //创建二值化图像
        Bitmap binarymap = null;
        binarymap = graymap.copy(Bitmap.Config.ARGB_8888, true);
        //依次循环，对图像的像素进行处理
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //得到当前像素的值
                int col = binarymap.getPixel(i, j);
                //得到alpha通道的值
                int alpha = col & 0xFF000000;
                //得到图像的像素RGB的值
                int red = (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue = (col & 0x000000FF);
                // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                //对图像进行二值化处理
                if (gray <= 95) {
                    gray = 0;
                } else {
                    gray = 255;
                }
                // 新的ARGB
                int newColor = alpha | (gray << 16) | (gray << 8) | gray;
                //设置新图像的当前像素值
                binarymap.setPixel(i, j, newColor);
            }
        }
        return binarymap;
    }


    public static Bitmap getBitmapFromSdCard(Context context,String path){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            Log.i(TAG, "getBitmapFromSdCard: "+path);
            File file = new File(path);
            if(file.exists())
            {
                Bitmap bm = BitmapFactory.decodeFile(path);
                return bm;
            }
        }
        else {
            ToastUtils.show(context, "找了半天没有发现 SD 卡呢");
            return null;
        }
        return null;
    }
}
