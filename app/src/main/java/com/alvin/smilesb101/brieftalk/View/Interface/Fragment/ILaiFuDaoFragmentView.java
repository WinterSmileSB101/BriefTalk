package com.alvin.smilesb101.brieftalk.View.Interface.Fragment;

import com.alvin.smilesb101.brieftalk.Bean.LaiFuDaoJoke;
import com.alvin.smilesb101.brieftalk.Bean.laiFuDaoJokeImage;

import java.util.ArrayList;

public interface ILaiFuDaoFragmentView {
    void onJokeSuccess(ArrayList<LaiFuDaoJoke> laiFuDaoJokes);
    void onJokeIamageSuccess(ArrayList<laiFuDaoJokeImage> laiFuDaoJokeImages);
    void onError(String error);
}
