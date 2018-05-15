package com.alvin.smilesb101.brieftalk.Bean.BmobTableBean;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

public class Word extends BmobObject implements Serializable,Comparable<Word>{
    String wordFrom;
    String word;
    String userId;
    String parts;
    boolean review_plan;

    public String getWordFrom() {
        return wordFrom;
    }

    public void setWordFrom(String wordFrom) {
        this.wordFrom = wordFrom;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isReview_plan() {
        return review_plan;
    }

    public void setReview_plan(boolean review_plan) {
        this.review_plan = review_plan;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    @Override
    public int compareTo(@NonNull Word o) {
        return this.word.compareTo(o.word);
    }
}
