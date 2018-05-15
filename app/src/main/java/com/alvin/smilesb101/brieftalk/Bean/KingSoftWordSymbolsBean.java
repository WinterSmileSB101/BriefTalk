package com.alvin.smilesb101.brieftalk.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class KingSoftWordSymbolsBean implements Serializable{
    String ph_en;
    String ph_am;
    String ph_other;
    String pg_en_mp3;
    String ph_am_mp3;
    String ph_tts_mp3;

    ArrayList<KingSoftWordPartsBen> parts;

    public String getPh_en() {
        return ph_en;
    }

    public void setPh_en(String ph_en) {
        this.ph_en = ph_en;
    }

    public String getPh_am() {
        return ph_am;
    }

    public void setPh_am(String ph_am) {
        this.ph_am = ph_am;
    }

    public String getPh_other() {
        return ph_other;
    }

    public void setPh_other(String ph_other) {
        this.ph_other = ph_other;
    }

    public String getPg_en_mp3() {
        return pg_en_mp3;
    }

    public void setPg_en_mp3(String pg_en_mp3) {
        this.pg_en_mp3 = pg_en_mp3;
    }

    public String getPh_am_mp3() {
        return ph_am_mp3;
    }

    public void setPh_am_mp3(String ph_am_mp3) {
        this.ph_am_mp3 = ph_am_mp3;
    }

    public String getPh_tts_mp3() {
        return ph_tts_mp3;
    }

    public void setPh_tts_mp3(String ph_tts_mp3) {
        this.ph_tts_mp3 = ph_tts_mp3;
    }

    public ArrayList<KingSoftWordPartsBen> getParts() {
        return parts;
    }

    public void setParts(ArrayList<KingSoftWordPartsBen> parts) {
        this.parts = parts;
    }
}
