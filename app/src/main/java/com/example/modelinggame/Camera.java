package com.example.modelinggame;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.tensorflow.lite.Interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Camera extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);

        //new


        // Example list of keypoints
//        List<float[]> keypoints = new ArrayList<>();
//        keypoints.add(new float[]{100, 150});
//        keypoints.add(new float[]{200, 250});
        // Add more keypoints as needed

        // Path to your image
        //String imagePath = "";

        // Create an instance of KeypointDrawer
//        int inputSize = 256;  // Set your input size
//        KeypointDrawer keypointDrawer = new KeypointDrawer(inputSize);
//
//        // Call the drawKeypoints method
//        Bitmap resultBitmap = keypointDrawer.drawKeypoints(keypoints, String.valueOf(R.drawable.clear));
//
//        // Display the resultBitmap in an ImageView
//        ImageView imageView = findViewById(R.id.silhouette);
//        imageView.setImageBitmap(resultBitmap);
        //end new
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                if (ContextCompat.checkSelfPermission(Camera.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    request.grant(request.getResources());
                } else {
                    ActivityCompat.requestPermissions(Camera.this,
                            new String[]{Manifest.permission.CAMERA},
                            CAMERA_PERMISSION_REQUEST_CODE);
                }
            }
        });
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        Intent intent1 = getIntent();
        String url = intent1.getStringExtra("url");
        webView.addJavascriptInterface(new JavaScriptSave(this, url), "AndroidInterface" );//, id

        webView.requestFocus();

        webView.loadUrl("file:///android_asset/indexCamera.html");

    }
}
