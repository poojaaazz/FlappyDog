package com.pooja.flappydog;
import android.content.Context;

import android.media.MediaPlayer;
public class SoundBank {
    Context context;
    MediaPlayer swoosh, point, hit, wing, buttonClick,backgroundMusic;
    private boolean isMuted = false;
    public  SoundBank(Context context){
        this.context = context;
        swoosh = MediaPlayer.create(context, R.raw.swoosh);
        point = MediaPlayer.create(context, R.raw.point);
        hit = MediaPlayer.create(context, R.raw.hit);
        wing = MediaPlayer.create(context, R.raw.wing);
        buttonClick = MediaPlayer.create(context, R.raw.button);
        backgroundMusic = MediaPlayer.create(context, R.raw.background);
        backgroundMusic.setLooping(true);// Loop the music
    }
    public void toggleMute() {
        if (backgroundMusic != null) {
            if (isMuted) {
                backgroundMusic.start(); // Resume music
            } else {
                backgroundMusic.pause(); // Pause music
            }
            isMuted = !isMuted; // Toggle state
        }
    }
    // Getter to check if the music is mute
    public boolean isMuted() {
        return isMuted;
    }
    public void playSwoosh(){
        if ((swoosh != null)) {
            swoosh.start();
        }
    }
    public void playPoint(){
        if ((point != null)) {
            point.start();
        }
    }
    public void playHit(){
        if ((hit != null)) {
            hit.start();
        }
    }
    public void playWing(){
        if ((wing != null)) {
            wing.start();
        }
    }
    public void playButtonClick(){
        if (buttonClick != null) {
            buttonClick.start();
        }
    }
//  *Play background music*
    public void playBackgroundMusic() {
        if (backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.start();
        }
    }
//  *Stop background music*
    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.pause();  // Pause instead of stop to resume later
        }
    }
}