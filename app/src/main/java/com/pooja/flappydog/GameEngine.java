package com.pooja.flappydog;

import android.app.Activity;

import android.content.Context;

import android.content.Intent;

import android.graphics.Canvas;

import android.graphics.Color;

import android.graphics.Paint;

import android.graphics.Typeface;

import android.util.Log;

import java.util.ArrayList;

import java.util.Random;

import android.graphics.Bitmap;

import android.graphics.Canvas;
public class GameEngine {
    BackgroundImage backgroundImage;
    Dog dog;
    static int gameState;
    ArrayList<Tube> tubes;
    Random random;
    int score;
    int scoringTube;
    Paint scorePaint;
    private int frameCounter = 0;
    private int frameDelay = 10;  // Change every 10 frames (adjustable)
    public GameEngine() {
        backgroundImage = new BackgroundImage();
        dog = new Dog();

        //0 = Not started
        //1 = playing
        //2 = GameOver
        gameState = 0;
        tubes = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < AppConstants.numberOfTubes; i++) {
            int tubeX = AppConstants.SCREEN_WIDTH + i * AppConstants.distanceBetweenTubes;
            //Get topTubeOffsetY
            int topTubeOffSetY = AppConstants.minTubeOffsetY +
                    random.nextInt(AppConstants.maxTubeOffsetY - AppConstants.minTubeOffsetY + 1);
            //now create Tube objects
            Tube tube = new Tube(tubeX, topTubeOffSetY);
            tubes.add(tube);
        }
        score = 0;
        scoringTube = 0;
        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(100);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }
    public void updateAndDrawTube(Canvas canvas) {

        if (gameState == 1) {

            Tube currentTube = tubes.get(scoringTube);

            if ((currentTube.getTubeX() < dog.getX() + AppConstants.getBitmapBank().getDogWidth() - 20) // Small margin

                    && (currentTube.getTopTubeOffsetY() > dog.getY()  // Top tube

                    || currentTube.getBottomTubeY() < (dog.getY() + AppConstants.getBitmapBank().getDogHeight()))) {  // Bottom tube

                //Go to GameOver Screen.
                gameState = 2;
                //Log.d("Game", "Over");
                AppConstants.getSoundBank().playHit();
                Context context = AppConstants.gameActivityContext;
                Intent intent = new Intent(context, GameOver.class);
                intent.putExtra("score", score);
                context.startActivity(intent);
                ((Activity) context).finish();
            } else if (tubes.get(scoringTube).getTubeX() < dog.getX() - AppConstants.getBitmapBank().getTubeWidth()) {
                score++;
                scoringTube++;
                if (scoringTube >= AppConstants.numberOfTubes) {

                    scoringTube = 0;
                }
                AppConstants.getSoundBank().playPoint();
            }
            for (int i = 0; i < AppConstants.numberOfTubes; i++) {

                if (tubes.get(i).getTubeX() < -AppConstants.getBitmapBank().getTubeWidth()) {

                    int newX = tubes.get(i).getTubeX() + (AppConstants.numberOfTubes * AppConstants.distanceBetweenTubes);

                    tubes.get(i).setTubeX(newX);

                    int topTubeOffsetY = AppConstants.minTubeOffsetY + random.nextInt(AppConstants.maxTubeOffsetY - AppConstants.minTubeOffsetY + 1);

                    tubes.get(i).setTopTubeOffsetY(topTubeOffsetY);

                    tubes.get(i).setTubeColor();
                }
                tubes.get(i).setTubeX(tubes.get(i).getTubeX() - AppConstants.tubeVelocity);

                if (tubes.get(i).getTubeColor() == 0) {

                    canvas.drawBitmap(AppConstants.getBitmapBank().getTubeTop(), tubes.get(i).getTubeX(), tubes.get(i).getTopTubeY(), null);

                    canvas.drawBitmap(AppConstants.getBitmapBank().getTubeBottom(), tubes.get(i).getTubeX(), tubes.get(i).getBottomTubeY(), null);

                } else {

                    canvas.drawBitmap(AppConstants.getBitmapBank().getRedTubeTop(), tubes.get(i).getTubeX(), tubes.get(i).getTopTubeY(), null);

                    canvas.drawBitmap(AppConstants.getBitmapBank().getRedTubeBottom(), tubes.get(i).getTubeX(), tubes.get(i).getBottomTubeY(), null);
                }
            }

        }

        drawScore(canvas);
    }

    public void drawScore(Canvas canvas) {

        String scoreStr = String.valueOf(score);

        int digitWidth = AppConstants.getBitmapBank().getNumberBitmap(0).getWidth();

        int x = AppConstants.SCREEN_WIDTH / 2 - (scoreStr.length() * digitWidth) / 2; // Center the score
        for (char c : scoreStr.toCharArray()) {

            int digit = c - '0'; // Convert char to int

            canvas.drawBitmap(AppConstants.getBitmapBank().getNumberBitmap(digit), x, 450, null);

            x += digitWidth; // Move right for the next digit
        }
    }
    public void updateAndDrawBackgroundImage(Canvas canvas) {

        backgroundImage.setX(backgroundImage.getX() - backgroundImage.getVelocity());

        if (backgroundImage.getX() < -AppConstants.getBitmapBank().getBackgroundWidth()) {

            backgroundImage.setX(0);

        }
        canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), backgroundImage.getX(), backgroundImage.getY(), null);

        if (backgroundImage.getX() < -(AppConstants.getBitmapBank().getBackgroundWidth() - AppConstants.SCREEN_WIDTH)) {

            canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), backgroundImage.getX() + AppConstants.getBitmapBank().getBackgroundWidth(), backgroundImage.getY(), null);

        }

        // Paint settings for "Tap Tap Tap" text

        Paint tapTextPaint = new Paint();

        tapTextPaint.setColor(Color.WHITE);

        tapTextPaint.setTextSize(80);

        tapTextPaint.setTextAlign(Paint.Align.CENTER);

        tapTextPaint.setTypeface(Typeface.DEFAULT_BOLD);

        tapTextPaint.setAlpha(170);
        // Show text before the game starts

        if (gameState == 0) {

            float textY = dog.getY() - 150;  // Position text above the dog

            if (textY < 100) textY = 100;    // Prevent text from going too high

            canvas.drawText("Tap !", AppConstants.SCREEN_WIDTH / 2, textY, tapTextPaint);

        }

        // Show text when the dog is falling without interaction

        else if (gameState == 1 && dog.getVelocity() > 0) {

            // After game starts, text appears BELOW the dog

            float textY = dog.getY() + AppConstants.getBitmapBank().getDogHeight() + 810;

            canvas.drawText("Tap Tap !", AppConstants.SCREEN_WIDTH / 2, textY, tapTextPaint);
        }
    }
    public void updateAndDrawDog(Canvas canvas) {

        if (gameState == 1) {

            if (dog.getY() < (AppConstants.SCREEN_HEIGHT - AppConstants.getBitmapBank().getDogHeight()) || dog.getVelocity() < 0) {

                dog.setVelocity(dog.getVelocity() + AppConstants.gravity);

                dog.setY(dog.getY() + dog.getVelocity());

            }

        }

        int currentFrame = dog.getCurrentFrame();

        if (frameCounter % frameDelay == 0) { // Change frame every frameDelay updates

            currentFrame++;

            if (currentFrame > dog.maxFrame) {

                currentFrame = 0;

            }
            dog.setCurrentFrame(currentFrame);
        }

        frameCounter = (frameCounter + 1) % Integer.MAX_VALUE;

        canvas.drawBitmap(AppConstants.getBitmapBank().getDog(currentFrame), dog.getX(), dog.getY(), null);
        //If it exceeds maxframe re-initialize to 0

        if (currentFrame > dog.maxFrame) {

            currentFrame = 0;

        }

        dog.setCurrentFrame(currentFrame);
    }

}