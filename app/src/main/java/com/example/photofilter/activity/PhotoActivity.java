package com.example.photofilter.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.photofilter.adapter.NavAdapter;
import com.example.photofilter.adapter.PhotoAdapter;
import com.example.photofilter.fragment.ColorFragment;
import com.example.photofilter.model.NavItem;

import java.util.ArrayList;

import info.androidhive.imagefilters.R;
/**
 * @author Luong
 *
 */
public class PhotoActivity extends AppCompatActivity {

    ImageView imageViewback,image;
    private float mScaleFactor = 1.0f;
    private ScaleGestureDetector scaleGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        scaleGestureDetector = new ScaleGestureDetector(this, new PhotoActivity.ScaleListener());
        imageViewback = findViewById(R.id.action_close);
        image = findViewById(R.id.image);
        imageViewback.setOnClickListener(v -> finish());
        Intent intent = getIntent();
        String imges = intent.getStringExtra("IMG");
        Glide.with(this)
                .load(imges)
                .into(image);

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {

            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            image.setScaleX(mScaleFactor);
            image.setScaleY(mScaleFactor);
            return true;
        }
    }
}

