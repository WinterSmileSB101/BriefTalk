package com.alvin.smilesb101.brieftalk.Presenter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;

import com.alvin.smilesb101.brieftalk.Bean.OcrReconizeBean;
import com.alvin.smilesb101.brieftalk.Bean.YouDaoTranslateDataBean;
import com.alvin.smilesb101.brieftalk.Model.YouDaoOCRModel;
import com.alvin.smilesb101.brieftalk.Model.YouDaoTranslateModel;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.IOcrTranslateFragment;
import com.youdao.ocr.online.Line;
import com.youdao.ocr.online.Line_Line;
import com.youdao.ocr.online.OCRParameters;
import com.youdao.ocr.online.OCRResult;
import com.youdao.ocr.online.Region;
import com.youdao.ocr.online.Region_Line;
import com.youdao.ocr.online.Word;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;

import java.util.List;

public class OcrTranslatePresenter {
    private IOcrTranslateFragment ocrView;

    private final static String TAG = OcrTranslatePresenter.class.getSimpleName();

    public OcrTranslatePresenter(IOcrTranslateFragment ocrView)
    {
        this.ocrView = ocrView;
    }

    public void ocrTranslate(OcrReconizeBean reconizeBean, final String appName, String transType, String lanType){
        YouDaoOCRModel.YOU_DAO_OCR_MODEL.startRecognize(reconizeBean, appName, transType, lanType, new YouDaoOCRModel.OcrTranslateListener() {
            @Override
            public void onSuccess(OCRResult ocrResult, String input) {
                String text = getTranslateResult(ocrResult);

                Log.i(TAG, "onSuccess: "+text);
                YouDaoTranslateModel.YOU_DAO_TRANSLATE_MODEL.translateWordOnline("英文", "中文", text, appName, new YouDaoTranslateModel.translateWordOnlineListener() {
                    @Override
                    public void onSuccess(Translate result, String input, String requestId) {
                        YouDaoTranslateDataBean translateDataBean = new YouDaoTranslateDataBean(System.currentTimeMillis(),result);
                        //Log.i(TAG, "onSuccess: "+result.getWebExplains().get(0).getMeans().get(0));
                        ocrView.showOcrTranslateOnline(translateDataBean,input);
                    }

                    @Override
                    public void onListBack(List<Translate> list, List<String> list1, List<TranslateErrorCode> list2, String s) {

                    }

                    @Override
                    public void onError(String Error) {
                        Log.i(TAG, "onError: "+Error);
                        ocrView.onError(Error);
                    }
                });

            }

            @Override
            public void onError(String error) {
                ocrView.onError(error);
            }
        });
    }

    public void ocrShow(OcrReconizeBean reconizeBean, String appName,String transType,String lanType){
        YouDaoOCRModel.YOU_DAO_OCR_MODEL.startRecognize(reconizeBean, appName, transType, lanType, new YouDaoOCRModel.OcrTranslateListener() {
            @Override
            public void onSuccess(OCRResult ocrResult, String input) {
                String text = getResult(ocrResult);
                SpannableString spannableString = new SpannableString(text);
                int start = 0;
                while (start<text.length()&&start>=0)
                {
                    int s = text.indexOf("文本",start);
                    int end = text.indexOf("：",s)+1;
                    if(s>=0){
                        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#808080"));
                        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(35);
                        UnderlineSpan underlineSpan = new UnderlineSpan();

                        spannableString.setSpan(sizeSpan,s,end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        spannableString.setSpan(colorSpan,s,end,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        spannableString.setSpan(underlineSpan,s,end-1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        start = end;
                    }
                    else
                    {
                        break;
                    }
                }
                ocrView.showOcrTranslate(spannableString,input);
            }

            @Override
            public void onError(String error) {
                ocrView.onError(error);
            }
        });
    }

    private String getTranslateResult(OCRResult result){
        StringBuilder sb = new StringBuilder();
        int i = 1;
        if(OCRParameters.TYPE_TEXT.equals(result.getType())){
            //按文本识别
            List<Region> regions = result.getRegions();
            for (Region region : regions) {
                List<Line> lines = region.getLines();
                for (Line line : lines) {
                    //sb.append("文本"+ i++ + "： ");
                    List<Word> words = line.getWords();
                    for (Word word : words) {
                        sb.append(word.getText()).append(" ");
                    }
                    sb.append("\n");
                }
            }
        }else{
            //按行识别
            List<Region_Line> regions = result.getRegions_Line();
            for (Region_Line region : regions) {
                List<Line_Line> lines = region.getLines();
                for (Line_Line line : lines) {
                    //sb.append("文本"+ i++ + "： ");
                    sb.append(line.getText());
                    sb.append("\n");
                }
            }
        }
        String text = sb.toString();
        if(!TextUtils.isEmpty(text)){
            text = text.substring(0, text.length() - 2);
        }
        return text;
    }

    private String getResult(OCRResult result) {
        StringBuilder sb = new StringBuilder();
        Log.i(TAG, "getResult: "+result.getJson());
        int i = 1;
        if(OCRParameters.TYPE_TEXT.equals(result.getType())){
            //按文本识别
            List<Region> regions = result.getRegions();
            for (Region region : regions) {
                List<Line> lines = region.getLines();
                for (Line line : lines) {
                    sb.append("文本"+ i++ + "： ");
                    List<Word> words = line.getWords();
                    for (Word word : words) {
                        sb.append(word.getText()).append(" ");
                    }
                    sb.append("\n");
                }
            }
        }else{
            //按行识别
            List<Region_Line> regions = result.getRegions_Line();
            for (Region_Line region : regions) {
                List<Line_Line> lines = region.getLines();
                for (Line_Line line : lines) {
                    sb.append("文本"+ i++ + "： ");
                    sb.append(line.getText());
                    sb.append("\n");
                }
            }
        }
        String text = sb.toString();
        if(!TextUtils.isEmpty(text)){
            text = text.substring(0, text.length() - 2);
        }
        return text;

    }
}
