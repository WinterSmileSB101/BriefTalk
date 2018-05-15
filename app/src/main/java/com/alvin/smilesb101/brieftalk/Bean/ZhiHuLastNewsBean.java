package com.alvin.smilesb101.brieftalk.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ZhiHuLastNewsBean implements Serializable{
    public String date;
    public ArrayList<ZhiHuStory> stories;
    public ArrayList<ZhiHuTopStory> top_stories;
}
