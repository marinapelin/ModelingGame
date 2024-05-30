package com.example.modelinggame;

import static android.content.ContentValues.TAG;

import java.io.ByteArrayOutputStream;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.modelinggame.ml.MobileNetModel;


import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TheGame extends AppCompatActivity {
    List<GameItem> gameItemList;
    MyApplication myApplication = (MyApplication) this.getApplication();
    ImageView imageView;
    String URL;

    TextView tv_id;
    ImageButton btn_start;
    int id;
    StringBuilder result = new StringBuilder();
    List<KeyPoint> baseImgResults = new ArrayList<>();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_game);

        gameItemList = myApplication.getGameItemList();
        imageView = findViewById(R.id.iv_silhouette);
        tv_id = findViewById(R.id.tv_forid);
        btn_start = findViewById(R.id.btn_start);


     
        Intent intent1 = getIntent();
        if (intent1 != null && intent1.hasExtra("id")) {
            id = intent1.getIntExtra("id", -1); // default value in case "id" is not found
            // Use the ID as needed
        } else {
            // Handle the case when "id" is not passed
            Log.e("the game", "No ID passed in the intent");
            id=0;
        }





        if(id>=0){
            for(GameItem g: gameItemList) {
                if (g.getId() == id) {

                    Log.d(TAG, "onCreate ID: " + g.getId());
                    tv_id.setText(String.valueOf(g.getId()));
                    Log.d(TAG, "onCreate URL: " + g.getImageURL());
                    URL = String.valueOf(g.getImageURL());
                    //URL="https://nycphoto.com/wp-content/uploads/2021/11/e10d3ab3-smiling-standing-facing-camera.jpg";
                }
            }
        }
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TheGame.this, Camera.class);
                intent.putExtra("url", URL);
                startActivity(intent);
            }
        });

        Glide.with(this)
                .asBitmap()
                .load(URL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        // Create a Bitmap with a silhouette effect (outline)
                        Bitmap newbitmap=null;
                        TensorBuffer outputFeature0 = run_ml_model_on_image(bitmap);
                        if(outputFeature0!=null){
                            int x=0;
                            for (int y = 0; y < 51; y=y+3) {
                                baseImgResults.add(new KeyPoint(x, new PointF(outputFeature0.getFloatArray()[y], outputFeature0.getFloatArray()[y+1]),outputFeature0.getFloatArray()[y+2]));
                                x++;
                            }
                            if(baseImgResults.size()!=0){
                                if(id>=0){
                                    for(GameItem g: gameItemList) {
                                        if (g.getId() == id) {
                                            g.setBaseImgResults(baseImgResults);
                                        }
                                    }
                                }
                            }

                            for (int i = 0; i < 51; i++) {
                                result.append(outputFeature0.getFloatArray()[i]).append(",  ");
                            }
                            //tv_id.setText(result);

                        }
                        // Set the silhouette image to the ImageView
                        if(newbitmap!=null){
                            imageView.setImageBitmap(newbitmap);
                        }
                    }
                });



    }
//    private Bitmap createSilhouetteBitmap(Bitmap originalBitmap) {
//        // Create a new bitmap with the same dimensions as the original bitmap
//        Bitmap silhouetteBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(silhouetteBitmap);
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setColor(Color.BLACK);
//        canvas.drawBitmap(originalBitmap, 0, 0, paint);
//
//        return silhouetteBitmap;
//    }

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

}

