package com.example.modelinggame;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class JavaScriptSave extends AppCompatActivity {
    private Context context;
    String url;
    public JavaScriptSave(Context context, String url) {// String idname , int idn
        this.context = context;
        this.url = url;
    }

    @JavascriptInterface
    public void saveImage(String imageData) {
        Log.d("MyTagGoesHere", "ImageData "+imageData);
        // Decode the base64-encoded image data
        byte[] decodedImage = Base64.decode(imageData, Base64.DEFAULT);
        Log.d("MyTagGoesHere", "ImageData "+decodedImage);
        // Create a Bitmap from the decoded image data
        String photoData = imageData;
        photoData = photoData.substring(photoData.indexOf(",") + 1);
        byte[] decodedString = Base64.decode(photoData.getBytes(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Log.d("MyTagGoesHere", "ImageData bitmap " + bitmap);
        // Get the directory where the image will be saved (e.g., Pictures folder)
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        long timeforname = System.currentTimeMillis();
        // Create a unique filename for the image
        String fileName = "img_" + timeforname + ".jpg";
        // Create the file object for the image
        File imageFile = new File(storageDir, fileName);
        // Save the image to the file
        try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(context, "Saved image", Toast.LENGTH_SHORT).show();
            Intent mediascanintent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediascanintent.setData(Uri.fromFile(imageFile));
            //sendBroadcast(intermediation);
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, ShowResult.class);
                    // Set any extras or flags if needed
                    intent.putExtra("imagename", fileName);
                    intent.putExtra("url", url);

                    context.startActivity(intent);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
