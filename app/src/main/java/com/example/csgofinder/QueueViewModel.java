package com.example.csgofinder;

import androidx.lifecycle.ViewModel;

public class QueueViewModel extends ViewModel {
    private boolean play;

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public boolean playToggle(){
        play = !play;
        return play;
    }
}
