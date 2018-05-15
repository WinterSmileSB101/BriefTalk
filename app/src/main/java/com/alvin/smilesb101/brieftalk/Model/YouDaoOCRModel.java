package com.alvin.smilesb101.brieftalk.Model;

import android.graphics.Bitmap;
import android.util.Log;

import com.alvin.smilesb101.brieftalk.Bean.OcrReconizeBean;
import com.alvin.smilesb101.brieftalk.View.Utils.ImageLoadUtils;
import com.youdao.ocr.online.ImageOCRecognizer;
import com.youdao.ocr.online.OCRListener;
import com.youdao.ocr.online.OCRParameters;
import com.youdao.ocr.online.OCRResult;
import com.youdao.ocr.online.OcrErrorCode;
import com.youdao.sdk.app.EncryptHelper;

import java.io.ByteArrayOutputStream;

public class YouDaoOCRModel {

    public final static YouDaoOCRModel YOU_DAO_OCR_MODEL  = new YouDaoOCRModel();

    private final  String TAG = "YouDaoOCRModel";
    private OCRParameters ops;


    public interface OcrTranslateListener{
        void onSuccess(OCRResult ocrResult,String input);
        void onError(String error);
    }

    public void startRecognize(OcrReconizeBean reconizeBean,String appName,String translateType,String lanType,final OcrTranslateListener listener){

        //默认按行识别，支持自动、中英繁、日韩、拉丁、印地语，其中自动识别不支持印地语识别，其他都可以
        // 当采用按字识别时，识别语言支持中英和英文识别，其中"zh-en"为中英识别，"en"参数表示只识别英文。若为纯英文识别，"zh-en"的识别效果不如"en"，请妥善选择
        ops = new OCRParameters.Builder()
                .source(appName)
                .timeout(100000)
                .type(translateType)
                .lanType(lanType)
                .build();

        final Bitmap bitmap = ImageLoadUtils.readBitmapFromFile(reconizeBean.getUri().getPath(),768);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,baos);

        byte[] datas = baos.toByteArray();
        String bases64 = EncryptHelper.getBase64(datas);
        int count = bases64.length();
        while (count>1.4*1024*1024){
            quality = quality - 10;
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            datas = baos.toByteArray();
            bases64 = EncryptHelper.getBase64(datas);
        }

        final String base64 = bases64;
        ImageOCRecognizer.getInstance(ops).recognize(base64, new OCRListener() {
            @Override
            public void onError(OcrErrorCode ocrErrorCode) {
                Log.i(TAG, "onError: "+ocrErrorCode.name());
                listener.onError("翻译被黑洞吃掉了("+ocrErrorCode.name()+")。");
            }

            @Override
            public void onResult(OCRResult ocrResult, String s) {
                listener.onSuccess(ocrResult,s);
            }
        });
    }
}
