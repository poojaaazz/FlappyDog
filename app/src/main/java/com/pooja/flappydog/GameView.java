package com.pooja.flappydog;
import android.content.Context;

import android.view.MotionEvent;

import android.view.SurfaceHolder;

import android.view.SurfaceView;

import androidx.annotation.NonNull;
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    GameThread gameThread;
    SoundBank soundBank;
    public GameView(Context context) {
        super(context);
        initView(context);
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if(!gameThread.isRunning()){
            gameThread = new GameThread(holder);
            gameThread.start();
        }else{
            gameThread.start();
        }
        soundBank.playBackgroundMusic();
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int i, int i1, int i2) {
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        if(gameThread.isRunning()){
            gameThread.setIsRunning(false);
            boolean retry = true;
            while(retry){
                try{
                    gameThread.join();
                    retry = false;
                }catch (InterruptedException e){
                }
            }
        }
        soundBank.stopBackgroundMusic();
    }
    void initView(Context context){
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        gameThread = new GameThread(holder);
        soundBank = new SoundBank(context);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        //Tap is detected
        if(action == MotionEvent.ACTION_DOWN){
            if(AppConstants.getGameEngine().gameState ==0){
                AppConstants.getGameEngine().gameState = 1;
                AppConstants.getSoundBank().playSwoosh();
            }else{
                AppConstants.getSoundBank().playWing();
            }
            AppConstants.getGameEngine().dog.setVelocity(AppConstants.VELOCITY_WHEN_JUMPED);
        }
        return true;
    }
}