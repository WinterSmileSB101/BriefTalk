package com.alvin.smilesb101.brieftalk.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowApiListBean<T> implements Serializable {
    int allPages;
    ArrayList<T> contentlist;

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public ArrayList<T> getContentlist() {
        return contentlist;
    }

    public void setContentlist(ArrayList<T> contentlist) {
        this.contentlist = contentlist;
    }
}
