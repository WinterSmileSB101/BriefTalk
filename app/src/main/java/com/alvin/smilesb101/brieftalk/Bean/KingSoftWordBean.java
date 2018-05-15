package com.alvin.smilesb101.brieftalk.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class KingSoftWordBean implements Serializable {
    String word_name;
    KingSoftWordExchange exchange;
    ArrayList<KingSoftWordSymbolsBean> symbols;

    public String getWord_name() {
        return word_name;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }

    public KingSoftWordExchange getExchange() {
        return exchange;
    }

    public void setExchange(KingSoftWordExchange exchange) {
        this.exchange = exchange;
    }

    public ArrayList<KingSoftWordSymbolsBean> getSymbols() {
        return symbols;
    }

    public void setSymbols(ArrayList<KingSoftWordSymbolsBean> symbols) {
        this.symbols = symbols;
    }
}
