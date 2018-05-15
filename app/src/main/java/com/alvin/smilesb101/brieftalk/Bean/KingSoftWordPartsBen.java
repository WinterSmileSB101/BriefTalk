package com.alvin.smilesb101.brieftalk.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class KingSoftWordPartsBen implements Serializable {
    /**
     * 词性
     */
    String part;

    ArrayList<String> means;

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public ArrayList<String> getMeans() {
        return means;
    }

    public void setMeans(ArrayList<String> means) {
        this.means = means;
    }
}
