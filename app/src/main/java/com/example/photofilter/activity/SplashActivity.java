package com.example.photofilter.activity;

import androidx.appcompat.app.AppCompatActivity;
import info.androidhive.imagefilters.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.photofilter.uitls.AppPreferences;
/**
 * @author Trung
 */
public class SplashActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!AppPreferences.isIntroData(SplashActivity.this)) {
                   startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this, StartActivity.class));
                    finish();
                }
            }
        }, 3000);

    }
}