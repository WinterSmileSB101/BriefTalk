package com.alvin.smilesb101.brieftalk.Bean;

public class ShowApiCommonBean<T> {
    int showapi_res_code;
    String showapi_res_error;
    ShowApiBodyBean<T> showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowApiBodyBean<T> getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowApiBodyBean<T> showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
}
