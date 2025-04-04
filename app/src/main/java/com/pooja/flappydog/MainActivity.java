package com.pooja.flappydog;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.animation.ObjectAnimator;

import android.animation.PropertyValuesHolder;

import android.animation.ValueAnimator;

import androidx.appcompat.app.AppCompatActivity;

import android.view.WindowManager;

import android.media.MediaPlayer;
public class MainActivity extends AppCompatActivity {
    private ObjectAnimator idleAnimation;
    private SoundBank soundBank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Make the activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Initialize App Constants
        AppConstants.initialization(this.getApplicationContext());
        soundBank = new SoundBank(this);
        // Play background music when the app starts
        soundBank.playBackgroundMusic();
        View startButton = findViewById(R.id.startButton);
        if (startButton != null) {
            startIdleAnimation(startButton);  // Start zoom animation
        }
    }
    private void startIdleAnimation(View view) {
        idleAnimation = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat("scaleX", 1.1f),
                PropertyValuesHolder.ofFloat("scaleY", 1.1f)
        );
        idleAnimation.setDuration(500);  // 500ms per cycle
        idleAnimation.setRepeatCount(ValueAnimator.INFINITE); // Loop indefinitely
        idleAnimation.setRepeatMode(ValueAnimator.REVERSE); // Bounce back
        idleAnimation.start();
    }
    public void startGame(View view) {
        // Stop idle animation when clicking
        if (idleAnimation != null && idleAnimation.isRunning()) {
            idleAnimation.cancel();
        }
        soundBank.playButtonClick();
        // Button click animation
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat("scaleX", 0.95f),
                PropertyValuesHolder.ofFloat("scaleY", 0.95f)
        );
        scaleDown.setDuration(100);
        scaleDown.setRepeatCount(1);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();
        // Start new activity after animation completes
        view.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
            finish();  // Close MainActivity after transition
        }, 200);
    }
}