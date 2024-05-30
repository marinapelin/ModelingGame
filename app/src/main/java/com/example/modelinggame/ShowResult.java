package com.example.modelinggame;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.modelinggame.ml.MobileNetModel;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ShowResult extends AppCompatActivity {
    StringBuilder result = new StringBuilder();
    ImageView imageView;
    ImageView imageView2;
    Bitmap img;
    Bitmap baseimg;
    ImageButton btn_tryagain;
    ImageButton btn_save;
    String imagename;
    StringBuilder resultBase = new StringBuilder();
    String url;
    List<KeyPoint> list1 = new ArrayList<>();
    List<KeyPoint> baseImgResults = new ArrayList<>();
    MyApplication myApplication = (MyApplication) this.getApplication();
    List<GameItem> gameItemList;
    int k=0;
    //Person p1;

    int numKeypoints = 18; // Number of keypoints per pose
    int dimensions = 2; // Each keypoint has 2 dimensions (x and y)
//2 because 3rd is visibility scale and we do not compare it
    // Initialize the arrays
    //float[][] keypoints1 = new float[numKeypoints][dimensions];
    //float[][] keypoints2 = new float[numKeypoints][dimensions];
public double comparePoses(List<KeyPoint> keypoints1, List<KeyPoint> keypoints2) {
    double totalDistance = 0.0;
    for (int i = 0; i < 17; i++) {
        KeyPoint k = keypoints1.get(i);
        float dx = k.getCoordinate().x;
        float dy = k.getCoordinate().y;

        KeyPoint k2 = keypoints2.get(i);
        float dx2 = k2.getCoordinate().x;
        float dy2 = k2.getCoordinate().y;

        float newx = dx-dx2;
        float newy = dy-dy2;

        double distance = Math.sqrt(newx * newx + newy * newy);
        totalDistance += distance;
    }

    return totalDistance;
}
public TensorBuffer run_ml_model_on_image(Bitmap img){
    img = Bitmap.createScaledBitmap(img, 192, 192, true );

    TensorBuffer outputFeature0 = null;
    try {
        MobileNetModel model = MobileNetModel.newInstance(getApplicationContext());

        // Creates inputs for reference.
        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 192, 192, 3}, DataType.UINT8);

        TensorImage tensorImage = new TensorImage(DataType.UINT8);
        tensorImage.load(img);
        ByteBuffer byteBuffer = tensorImage.getBuffer();
        inputFeature0.loadBuffer(byteBuffer);

        // Runs model inference and gets result.
        MobileNetModel.Outputs outputs = model.process(inputFeature0);
        outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
        model.close();
        return outputFeature0;
    } catch (IOException e) {
        // TODO Handle the exception
    }
    return outputFeature0; // returns null;

}
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        imagename = getIntent().getExtras().getString("imagename");
        url = getIntent().getExtras().getString("url");
        TextView tv = findViewById(R.id.tv_forid);
        imageView = findViewById(R.id.iv_resultphoto);
        imageView2 = findViewById(R.id.imageView2);
        // Get the directory where the image will be saved (e.g., Pictures folder)
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        // Create the file object for the image
        File imageFile = new File(storageDir, imagename);
        imageView.setImageURI(Uri.fromFile(imageFile));


        //ml?

        Uri uri = Uri.fromFile(imageFile);
        try {
            img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //ntArrayOf(1, 192, 192, 3), DataType.UINT8
        //img = Bitmap.createScaledBitmap(img, 192, 192, true );
        TensorBuffer outputFeature0 = run_ml_model_on_image(img);
        if(outputFeature0!=null){
            int j=0;
            for (int i = 0; i < 51; i=i+3) {
                list1.add(new KeyPoint(j, new PointF(outputFeature0.getFloatArray()[i], outputFeature0.getFloatArray()[i+1]),outputFeature0.getFloatArray()[i+2]));
                //keypoints1[j][0] = outputFeature0.getFloatArray()[i]; // x-coordinate
                //keypoints1[j][1] = outputFeature0.getFloatArray()[i+1]; // y-coordinate
                j++;
            }
            for (int i = 0; i < 51; i++) {
                result.append(outputFeature0.getFloatArray()[i]).append(",  ");
            }
            //tv.setText(result);
        }
        //end ml
        // Compare poses
        gameItemList = myApplication.getGameItemList();
        if(url!=null){
            for(GameItem g: gameItemList) {
                String u=g.getImageURL();
                if (u.equals(url)) {
                    baseImgResults=g.getBaseImgResults();
                }
            }
        }
        double averageDistance = comparePoses(list1, baseImgResults);
        TextView tv_result = findViewById(R.id.resulttext);
        double percentCompleted=(1-averageDistance)*100;
        String roundedResult = String.format("%.1f", percentCompleted);

        tv_result.setText("You made it! "+roundedResult+"% right! Keep going and try new pose challenge!");


        //imageView.setImageURI(fileUri);
        btn_tryagain = findViewById(R.id.btn_again);
        btn_tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowResult.this, Camera.class);
                startActivity(intent);
            }
        });
        btn_save = findViewById(R.id.btn_tomenu);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowResult.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        // Create a Bitmap with a silhouette effect (outline)
                        imageView2.setImageBitmap(bitmap);

                    }
                });
    }

//    public static double comparePoses(float[][] keypoints1, float[][] keypoints2) {
//
//        int numKeypoints = keypoints1.length;
//
//        double totalDistance = 0.0;
//        for (int i = 0; i < numKeypoints; i++) {
//            double dx = keypoints1[i][0] - keypoints2[i][0];
//            double dy = keypoints1[i][1] - keypoints2[i][1];
//            double distance = Math.sqrt(dx * dx + dy * dy);
//            totalDistance += distance;
//        }
//
//        double averageDistance = totalDistance / numKeypoints;
//        return averageDistance;
//    }

}
