package com.example.modelinggame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import org.tensorflow.lite.Interpreter;

import java.util.List;

public class KeypointDrawer {

    private int inputSize;

    public KeypointDrawer(int inputSize) {
        this.inputSize = inputSize;
    }

    public Bitmap drawKeypoints(List<float[]> keypoints1, String image1) {
        Bitmap bitmap = BitmapFactory.decodeFile(image1);

        // Resize and pad the image to keep the aspect ratio and fit the expected size
        //Bitmap inputImage = resizeAndPad(bitmap, inputSize, inputSize);
       // Bitmap displayImage = resizeAndPad(bitmap, 720, 1080);

        Bitmap outputOverlay = drawPredictionOnImage(bitmap, keypoints1);

        return outputOverlay;
    }

    private Bitmap resizeAndPad(Bitmap bitmap, int targetWidth, int targetHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float aspectRatio = (float) width / height;

        int newWidth = targetWidth;
        int newHeight = targetHeight;
        if (width > height) {
            newHeight = (int) (newWidth / aspectRatio);
        } else {
            newWidth = (int) (newHeight * aspectRatio);
        }

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        Bitmap paddedBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(paddedBitmap);
        canvas.drawColor(Color.BLACK);
        int offsetX = (targetWidth - newWidth) / 2;
        int offsetY = (targetHeight - newHeight) / 2;
        canvas.drawBitmap(resizedBitmap, offsetX, offsetY, null);

        return paddedBitmap;
    }

    private Bitmap drawPredictionOnImage(Bitmap bitmap, List<float[]> keypoints) {
        //tmap outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5f);

        for (float[] keypoint : keypoints) {
            float x = keypoint[0];
            float y = keypoint[1];
            canvas.drawCircle(x, y, 10f, paint);
        }

        return bitmap;
    }
}
