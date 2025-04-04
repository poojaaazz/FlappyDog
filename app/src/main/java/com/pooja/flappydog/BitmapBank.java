package com.pooja.flappydog;

import android.content.res.Resources;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

public class BitmapBank {

    Bitmap background;

    Bitmap[] dog;

    Bitmap tubeTop, tubeBottom;

    Bitmap redTubeTop, redTubeBottom;

    Bitmap[] numbers;

    public BitmapBank(Resources res) {





        background = BitmapFactory.decodeResource(res, R.drawable.bgbg);

        background = scaleImage(background);

        dog = new Bitmap[4];

        dog[0] = BitmapFactory.decodeResource(res, R.drawable.dog_frame1);

        dog[1] = BitmapFactory.decodeResource(res, R.drawable.dog_frame2);

        dog[2] = BitmapFactory.decodeResource(res, R.drawable.dog_frame3);

        dog[3] = BitmapFactory.decodeResource(res, R.drawable.dog_frame4);

        int newWidth = dog[0].getWidth() / 4;  // Reduce width

        int newHeight = dog[0].getHeight() / 4; // Reduce height

        for (int i = 0; i < dog.length; i++) {

            dog[i] = Bitmap.createScaledBitmap(dog[i], newWidth, newHeight, false);

        }

        tubeTop = BitmapFactory.decodeResource(res, R.drawable.tube_top);

        tubeBottom = BitmapFactory.decodeResource(res, R.drawable.tube_bottom);

        redTubeBottom = BitmapFactory.decodeResource(res, R.drawable.red_tube_bottom);

        redTubeTop = BitmapFactory.decodeResource(res, R.drawable.red_tube_top);

        // Scale the tubes to make them thinner

        int newTubeWidth = tubeTop.getWidth() / 2;  // Reduce width by 50%

        int newTubeHeight = tubeTop.getHeight();  // Keep original height

        tubeTop = Bitmap.createScaledBitmap(tubeTop, newTubeWidth, newTubeHeight, false);

        tubeBottom = Bitmap.createScaledBitmap(tubeBottom, newTubeWidth, newTubeHeight, false);



        int newRedTubeWidth = redTubeTop.getWidth() / 2;  // Reduce width by 50%

        int newRedTubeHeight = redTubeTop.getHeight();  // Keeping original height



        redTubeTop = Bitmap.createScaledBitmap(redTubeTop, newTubeWidth, newTubeHeight, false);

        redTubeBottom = Bitmap.createScaledBitmap(redTubeBottom, newTubeWidth, newTubeHeight, false);

        numbers = new Bitmap[10];

        numbers[0] = BitmapFactory.decodeResource(res, R.drawable.num_0);

        numbers[1] = BitmapFactory.decodeResource(res, R.drawable.num_1);

        numbers[2] = BitmapFactory.decodeResource(res, R.drawable.num_2);

        numbers[3] = BitmapFactory.decodeResource(res, R.drawable.num_3);

        numbers[4] = BitmapFactory.decodeResource(res, R.drawable.num_4);

        numbers[5] = BitmapFactory.decodeResource(res, R.drawable.num_5);

        numbers[6] = BitmapFactory.decodeResource(res, R.drawable.num_6);

        numbers[7] = BitmapFactory.decodeResource(res, R.drawable.num_7);

        numbers[8] = BitmapFactory.decodeResource(res, R.drawable.num_8);

        numbers[9] = BitmapFactory.decodeResource(res, R.drawable.num_9);

    }



// Method to get the correct number image

    public Bitmap getNumberBitmap(int num) {

        if (num < 0 || num > 9 || numbers[num] == null) {

            return numbers[0]; // Default to zero instead of crashing

        }

        return numbers[num];

    }









//Return Red Tube-Top Bitmap

    public Bitmap getRedTubeTop(){

        return redTubeTop;

    }

//return red Tube-Bottom Bitmap

    public Bitmap getRedTubeBottom(){

        return redTubeBottom;

    }



//return tube-top btmap

    public Bitmap getTubeTop(){

        return tubeTop;

    }

//Return Tube-Bottom Bitmap

    public Bitmap getTubeBottom(){

        return tubeBottom;

    }

//Return Tube-width

    public int getTubeWidth(){

        return tubeTop.getWidth();

    }

//Return Tube-height

    public int getTubeHeight(){

        return tubeTop.getHeight();

    }

//Return dog bitmap of frame

    public Bitmap getDog(int frame){

        return dog[frame];

    }

    public int getDogWidth(){

        return dog[0].getWidth();

    }

    public int getDogHeight(){

        return dog[0].getHeight();

    }



//Return background bitmap

    public Bitmap getBackground(){

        return background;

    }

//Return background width

    public int getBackgroundWidth(){

        return background.getWidth();

    }
//Return background height

    public int getBackgroundHeight(){

        return background.getHeight();

    }

    public Bitmap scaleImage(Bitmap bitmap){

        float widthHeightRatio = getBackgroundWidth() / getBackgroundHeight();

        //I am multiplying widthHeightRatio with screenHeight to get scaled width of the bitmap

        //Then call createScaledBitmap() to create a new bitmap, scaled from an existing bitmap, when possible.
        int backgroundScaledWidth = (int) widthHeightRatio * AppConstants.SCREEN_HEIGHT;

        return Bitmap.createScaledBitmap(bitmap, backgroundScaledWidth, AppConstants.SCREEN_HEIGHT, false);

    }

}