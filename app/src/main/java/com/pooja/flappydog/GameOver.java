package com.pooja.flappydog;

import android.content.Intent;

import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.View;

import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
public class GameOver extends AppCompatActivity {
    TextView tvScore, tvPersonalBest;
    SoundBank soundBank; //  SoundBank instance
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        soundBank = new SoundBank(this); // Initialize SoundBank
        int score = getIntent().getExtras().getInt("score");
        SharedPreferences pref = getSharedPreferences("MyPref", 0);
        int scoreSP = pref.getInt("scoreSP", 0);
        SharedPreferences.Editor editor = pref.edit();
        if(score > scoreSP){
            scoreSP = score;
            editor.putInt("scoreSP", scoreSP);
            editor.apply();
        }
        tvScore = findViewById(R.id.tvScore);
        tvPersonalBest = findViewById(R.id.tvPersonalBest);
        tvScore.setText("Score: " + score);
        tvPersonalBest.setText("Highest Score: " + scoreSP);
        ImageView buttonRestart = findViewById(R.id.buttonRestart);
        ImageView buttonExit = findViewById(R.id.buttonExit);
        // Restart button click (with sound)
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundBank.playButtonClick(); //  Play button sound
                animateButton(v);
                Intent intent = new Intent(GameOver.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Exit button click (with sound)
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundBank.playButtonClick();
                animateButton(v); //  Play button sound
                finish(); // Close the app
            }
        });
    }
    // Method to apply scaling animation
    private void animateButton(View view) {
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat("scaleX", 0.95f),
                PropertyValuesHolder.ofFloat("scaleY", 0.95f)
        );
        scaleDown.setDuration(100);  // Duration of the scaling animation
        scaleDown.setRepeatCount(1);  // Scale once
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);  // Reverse to original size
        scaleDown.start();
    }
}