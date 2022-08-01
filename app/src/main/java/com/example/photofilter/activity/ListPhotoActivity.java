package com.example.photofilter.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import info.androidhive.imagefilters.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.photofilter.adapter.NavAdapter;
import com.example.photofilter.adapter.PhotoAdapter;
import com.example.photofilter.adapter.PhotoAdapterListener;
import com.example.photofilter.fragment.ColorFragment;
import com.example.photofilter.model.NavItem;

import java.util.ArrayList;
/**
 * @author Hieu
 *
 */
public class ListPhotoActivity extends AppCompatActivity implements PhotoAdapterListener {


    private RecyclerView recyclerView;
    CoordinatorLayout coordinatorLayout;
    PhotoAdapter adapter;
    ImageView imageViewback, imageViewClose,image;
    private ArrayList<String> imagePaths;
    boolean isShow = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photo);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        imageViewback = findViewById(R.id.action_back);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ListPhotoActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        imagePaths = new ArrayList<>();
        getImagePath();

        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          finish();
            }
        });
    }

    @Override
    public void onFilterSelected(String position) {
        Intent intent = new Intent(ListPhotoActivity.this, PhotoActivity.class);
        intent.putExtra("IMG", position);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if ( isShow ){
            coordinatorLayout.setVisibility(View.GONE);
            isShow = false;
        }else {
            super.onBackPressed();
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private void getImagePath() {
        boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

        if (isSDPresent) {

            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
            final String orderBy = MediaStore.Images.Media._ID;

            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);

            int count = cursor.getCount();

            for (int i = 0; i < count; i++) {

                cursor.moveToPosition(i);
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                imagePaths.add(cursor.getString(dataColumnIndex));
            }
            adapter = new PhotoAdapter(ListPhotoActivity.this, imagePaths, this);
            recyclerView.setAdapter(adapter);
            cursor.close();
        }
    }
}