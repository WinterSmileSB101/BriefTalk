package com.alvin.smilesb101.brieftalk.Bean.BmobTableBean;

import android.support.annotation.NonNull;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class UserInfo extends BmobObject implements Serializable {
    String username;
    String userId;
    int wordsCount;
    int searchTime;
    int readTime;
    int signInTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }

    public int getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(int searchTime) {
        this.searchTime = searchTime;
    }

    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

    public int getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(int signInTime) {
        this.signInTime = signInTime;
    }
}
