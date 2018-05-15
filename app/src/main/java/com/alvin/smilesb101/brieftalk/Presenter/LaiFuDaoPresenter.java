package com.alvin.smilesb101.brieftalk.Presenter;

import com.alvin.smilesb101.brieftalk.Bean.LaiFuDaoJoke;
import com.alvin.smilesb101.brieftalk.Bean.laiFuDaoJokeImage;
import com.alvin.smilesb101.brieftalk.Model.LaiFuDaoModel;
import com.alvin.smilesb101.brieftalk.View.Interface.Fragment.ILaiFuDaoFragmentView;

import java.util.ArrayList;

public class LaiFuDaoPresenter {
    public ILaiFuDaoFragmentView laiFuDaoFragmentView;

    public LaiFuDaoPresenter(ILaiFuDaoFragmentView view)
    {
        laiFuDaoFragmentView = view;
    }

    public void getLaiFuDaoJoke(){
        LaiFuDaoModel.LAI_FU_DAO_MODEL.getLaiFuDaoJoke(new LaiFuDaoModel.laifuDaoListener() {
            @Override
            public void onJokeSuccess(ArrayList<LaiFuDaoJoke> laiFuDaoJokes) {
                laiFuDaoFragmentView.onJokeSuccess(laiFuDaoJokes);
            }

            @Override
            public void onJokeImageSuccess(ArrayList<laiFuDaoJokeImage> laiFuDaoJokeImages) {

            }

            @Override
            public void onError(String error) {
                laiFuDaoFragmentView.onError(error);
            }
        });
    }

    public void getLaiFuDaoJokeImage(){
        LaiFuDaoModel.LAI_FU_DAO_MODEL.getLaiFuDaoJokeImage(new LaiFuDaoModel.laifuDaoListener() {
            @Override
            public void onJokeSuccess(ArrayList<LaiFuDaoJoke> laiFuDaoJokes) {

            }

            @Override
            public void onJokeImageSuccess(ArrayList<laiFuDaoJokeImage> laiFuDaoJokeImages) {
                laiFuDaoFragmentView.onJokeIamageSuccess(laiFuDaoJokeImages);
            }

            @Override
            public void onError(String error) {
                laiFuDaoFragmentView.onError(error);
            }
        });
    }
}
