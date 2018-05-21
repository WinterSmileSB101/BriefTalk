package com.alvin.smilesb101.brieftalk.Bean;

import java.util.ArrayList;

public class ShowApiBodyBean<T> {
    int ret_code;
    ArrayList<T> list;

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }
}
