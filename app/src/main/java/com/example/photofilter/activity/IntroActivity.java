package com.example.photofilter.activity;

import androidx.appcompat.app.AppCompatActivity;
import info.androidhive.imagefilters.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photofilter.adapter.IntroViewPagerAdapter;
import com.example.photofilter.model.ScreenIntroItem;
import com.example.photofilter.uitls.AppPreferences;
import com.example.photofilter.uitls.NonSwipeableViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Truong
 *
 */
public class IntroActivity extends AppCompatActivity {
    private NonSwipeableViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter ;

    TabLayout tabIndicator;
    Button btnNext;
    int position = 0 ;
    Button btnGetStarted;
    //Animation btnAnim ;
    TextView tvSkip;
    //ImageView imageViewnam, imageViewnu, imageViewnamnu;
    //EditText editTextname;
    View linearLayout1 ;
    int giioitinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        // btnAnim = AnimationUtils.loadAnimation(getContext(),R.anim.buttonintro_animation);
        tvSkip = findViewById(R.id.tv_skip);


        final List<ScreenIntroItem> mList = new ArrayList<>();
        mList.add(new ScreenIntroItem("Hello!","PhotoFilter","Welcome to PhotoFilter \n The world's leading image editing app", R.drawable.picture1));
        mList.add(new ScreenIntroItem("Hello!","PhotoFilter","       Experience the superior features", R.drawable.picture2));
        mList.add(new ScreenIntroItem("Hello!","PhotoFilter","       Delight, passion, creativity", R.drawable.picture3));
        mList.add(new ScreenIntroItem("Hello!","PhotoFilter","Just a few simple steps you will have a beautiful photo!!!", R.drawable.picture1));

        screenPager =findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1) {
                    loaddLastScreen();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabIndicator.setupWithViewPager(screenPager);

        btnNext.setOnClickListener(v -> {

            position = screenPager.getCurrentItem();
            if (position < mList.size()) {

                position++;
                screenPager.setCurrentItem(position);

            }

            if (position == mList.size()-1) {
                loaddLastScreen();
                //screenPager.setPagingEnabled(false);
            }


        });


        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1) {
                    loaddLastScreen();

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        btnGetStarted.setOnClickListener(v -> {
            AppPreferences.setIntroData(this);
            startActivity(new Intent(IntroActivity.this, StartActivity.class));
            finish();
        });


        tvSkip.setOnClickListener(v -> {
            screenPager.setCurrentItem(mList.size());

        });


    }
    private void loaddLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

    }
}