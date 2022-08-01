package com.example.photofilter.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.photofilter.uitls.BitmapUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import info.androidhive.imagefilters.R;
import info.androidhive.imagefilters.databinding.ActivityStartBinding;
/**
 * @author Trung
 */
public class StartActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int PICK_FROM_GALLARY = 102;
    private static final int PICK_FROM_CAMERA = 103;
    private ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        binding.storage.setOnClickListener(v -> checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE));
        binding.camera.setOnClickListener(v -> checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE));
        binding.allphoto.setOnClickListener(v -> startActivity(new Intent(StartActivity.this, ListPhotoActivity.class)));
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(StartActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(StartActivity.this, new String[]{permission}, requestCode);
        } else {
            if (requestCode == STORAGE_PERMISSION_CODE) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLARY);
            } else if (requestCode == CAMERA_PERMISSION_CODE) {
                Intent camera_intent
                        = new Intent(MediaStore
                        .ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, PICK_FROM_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent camera_intent
                        = new Intent(MediaStore
                        .ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, PICK_FROM_CAMERA);
            } else {
                Toast.makeText(StartActivity.this, R.string.camerapermistion, Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLARY);
            } else {
                Toast.makeText(StartActivity.this, R.string.storepermiss, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK) {

                    Bitmap photo = (Bitmap)data.getExtras()
                            .get("data");
                    File cacheDir = getBaseContext().getCacheDir();
                    File f = new File(cacheDir, "pic");
                    try {
                        FileOutputStream out = new FileOutputStream(
                                f);
                        photo.compress(
                                Bitmap.CompressFormat.JPEG,
                                100, out);
                        out.flush();
                        out.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                break;

            case PICK_FROM_GALLARY:

                if (resultCode == Activity.RESULT_OK) {
                    //pick image from gallery
                    Uri selectedImage = data.getData();
                    Bitmap originalImage;
                    originalImage = BitmapUtils.getBitmapFromGallery(this, selectedImage, 300, 300);
                    File cacheDir = getBaseContext().getCacheDir();
                    File f = new File(cacheDir, "pic");
                    try {
                        FileOutputStream out = new FileOutputStream(
                                f);
                        originalImage.compress(
                                Bitmap.CompressFormat.JPEG,
                                100, out);
                        out.flush();
                        out.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}