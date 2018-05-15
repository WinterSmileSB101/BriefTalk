package com.alvin.smilesb101.brieftalk.Bean;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;

import java.io.Serializable;

public class OcrReconizeBean implements Parcelable{

    public OcrReconizeBean(){}

    protected OcrReconizeBean(Parcel in) {
        uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<OcrReconizeBean> CREATOR = new Creator<OcrReconizeBean>() {
        @Override
        public OcrReconizeBean createFromParcel(Parcel in) {
            return new OcrReconizeBean(in);
        }

        @Override
        public OcrReconizeBean[] newArray(int size) {
            return new OcrReconizeBean[size];
        }
    };

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public SpannableString getText() {
        return text;
    }

    public void setText(SpannableString text) {
        this.text = text;
    }

    Uri uri;
    SpannableString text;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(uri,flags);
        dest.writeValue(text);
    }
}
