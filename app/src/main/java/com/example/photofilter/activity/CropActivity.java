package com.example.photofilter.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import info.androidhive.imagefilters.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.photofilter.processor.CropUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * @author Trung
 *
 */
public class CropActivity extends AppCompatActivity {
        CropUtils.CropImageView imageview1;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_crop);

            Button button1 = findViewById(R.id.done_button);
            imageview1 = findViewById(R.id.cropimageview);
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
            File cacheDir = getBaseContext().getCacheDir();
            File f = new File(cacheDir, "pic");
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            imageview1.setImageBitmap(bitmap);

            button1.setOnClickListener(v -> {

                imageview1.post(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap2 = imageview1.getCroppedImage();
                        File cacheDir1 = getBaseContext().getCacheDir();
                        File f1 = new File(cacheDir1, "pic");
                        try {
                            FileOutputStream out = new FileOutputStream(
                                    f1);
                            bitmap2.compress(
                                    Bitmap.CompressFormat.JPEG,
                                    100, out);
                            out.flush();
                            out.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        finish();
                    }
                });


            });
        }
}

