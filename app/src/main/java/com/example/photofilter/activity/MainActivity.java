package com.example.photofilter.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.photofilter.adapter.NavAdapter;
import com.example.photofilter.adapter.NavAdapterListener;
import com.example.photofilter.fragment.BrightnessFragment;
import com.example.photofilter.fragment.ColorFragment;
import com.example.photofilter.fragment.ContrastFragment;
import com.example.photofilter.fragment.EditImageFragmentListener;
import com.example.photofilter.fragment.FiltersListFragment;
import com.example.photofilter.fragment.SaturationFragment;
import com.example.photofilter.fragment.ToneFragment;
import com.example.photofilter.fragment.VintageFragment;
import com.example.photofilter.model.NavItem;
import com.example.photofilter.processor.VignetteSubfilter;
import com.zomato.photofilters.imageprocessors.Filter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.androidhive.imagefilters.R;

import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubFilter;

/**
 * @author Trung
 *
 */
public class MainActivity extends AppCompatActivity implements FiltersListFragment.FiltersListFragmentListener , EditImageFragmentListener, NavAdapterListener{
    public static final String IMAGE_NAME = "1.jpg";
    private static final String IMAGEURI = "imageUri";
    public static  Uri myUri;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.image_preview)
    ImageView imagePreview;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.action_back)
    ImageView imgback;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.action_save)
    ImageView imgsave;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static Bitmap originalImage;
    Bitmap filteredImage;
    Bitmap finalImage;

    FiltersListFragment filtersListFragment;
    BrightnessFragment biBrightnessFragment;
    ContrastFragment contrastFragment;
    SaturationFragment  saturationFragment;
    ToneFragment toneFragment;
    VintageFragment vintageFragment;
    ColorFragment colorFragment;
    ArrayList<NavItem> arrayList;
    NavAdapter mAdapter;
    private float mScaleFactor = 1.0f;
    int brightnessFinal = 0;
    float saturationFinal = 1.0f;
    float contrastFinal = 1.0f;

    float redFinal = 0;
    float greenFinal = 0;
    float buleFinal = 0;
    float toneFinal = 0;
    int vintageFinal = 0;
    private ScaleGestureDetector scaleGestureDetector;


    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );



        imgback.setOnClickListener(view -> finish());

        imgsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePreview.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) imagePreview.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                File cacheDir = getBaseContext().getCacheDir();
                File f = new File(cacheDir, "pic");
                try {
                    FileOutputStream out = new FileOutputStream(
                            f);
                    bitmap.compress(
                            Bitmap.CompressFormat.JPEG,
                            100, out);
                    out.flush();
                    out.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getBaseContext(), DoneActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        File cacheDir = getBaseContext().getCacheDir();
        File f = new File(cacheDir, "pic");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        loadImage(bitmap);
        setupViewPager(viewPager);
        arrayList = new ArrayList<>();
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        arrayList.add(new NavItem(0,R.drawable.ic_baseline_brightness,getString(R.string.lbl_brightness)));
        arrayList.add(new NavItem(1,R.drawable.ic_baseline_contrast,getString(R.string.lbl_contrast)));
        arrayList.add(new NavItem(2,R.drawable.ic_baseline_whatshot,getString(R.string.lbl_saturation)));
        arrayList.add(new NavItem(3,R.drawable.ic_baseline_crop_24,getString(R.string.crop)));
        arrayList.add(new NavItem(4,R.drawable.ic_baseline_photo_filter,getString(R.string.filters)));
        arrayList.add(new NavItem(5,R.drawable.ic_baseline_tonality_24,getString(R.string.tone)));
        arrayList.add(new NavItem(5,R.drawable.ic_baseline_color_lens_24,getString(R.string.color)));
        arrayList.add(new NavItem(5,R.drawable.ic_baseline_filter_vintage_24,getString(R.string.vignette)));
        mAdapter = new NavAdapter(MainActivity.this, arrayList, this);
        recyclerView.clearOnChildAttachStateChangeListeners();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        setupViewPager(viewPager);
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        // inside on touch event method we are calling on
        // touch event method and passing our motion event to it.
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        // on below line we are creating a class for our scale
        // listener and  extending it with gesture listener.
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {

            // inside on scale method we are setting scale
            // for our image in our image view.
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            // on below line we are setting
            // scale x and scale y to our image view.
            imagePreview.setScaleX(mScaleFactor);
            imagePreview.setScaleY(mScaleFactor);
            return true;
        }
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        filtersListFragment = new FiltersListFragment();
        filtersListFragment.setListener(this);

        biBrightnessFragment = new BrightnessFragment();
        biBrightnessFragment.setListener(this);

        contrastFragment = new ContrastFragment();
        contrastFragment.setListener(this);

        saturationFragment = new SaturationFragment();
        saturationFragment.setListener(this);

        colorFragment = new ColorFragment();
        colorFragment.setListener(this);

        toneFragment = new ToneFragment();
        toneFragment.setListener(this);
        vintageFragment = new VintageFragment();
        vintageFragment.setListener(this);



        adapter.addFragment(biBrightnessFragment, getString(R.string.edit));
        adapter.addFragment(contrastFragment, getString(R.string.edit));
        adapter.addFragment(saturationFragment, getString(R.string.edit));
        adapter.addFragment(filtersListFragment, getString(R.string.filters));
        adapter.addFragment(toneFragment, getString(R.string.filters));
        adapter.addFragment(colorFragment, getString(R.string.filters));
        adapter.addFragment(vintageFragment, getString(R.string.filters));


        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFilterSelected(Filter filter) {
        // reset image controls
        //resetControls();

        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);

        imagePreview.setImageBitmap(filter.processFilter(filteredImage));

        finalImage = filteredImage.copy(Bitmap.Config.ARGB_8888, true);
    }

    @Override
    public void onBrightnessChanged(final int brightness) {
        brightnessFinal = brightness;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new com.example.photofilter.processor.BrightnessSubFilter(brightness));
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onSaturationChanged(final float saturation) {
        saturationFinal = saturation;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new com.example.photofilter.processor.SaturationSubfilter(saturation));
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onContrastChanged(final float contrast) {
        contrastFinal = contrast;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new ContrastSubFilter(contrast));
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onToneChanged(float tone) {
        contrastFinal = tone;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new ContrastSubFilter(tone));
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onColorChanged(float read, float green, float blue) {

        Filter myFilter = new Filter();
        myFilter.addSubFilter(new ColorOverlaySubFilter(10,read,green, blue));
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onVintageChanged(int vintage) {
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new VignetteSubfilter(this, vintage));
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onEditStarted() {

    }

    @Override
    public void onEditCompleted() {
        final Bitmap bitmap = filteredImage.copy(Bitmap.Config.ARGB_8888, true);

        com.example.photofilter.processor.Filter myFilter = new com.example.photofilter.processor.Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightnessFinal));
        myFilter.addSubFilter(new ContrastSubFilter(contrastFinal));
        myFilter.addSubFilter(new SaturationSubfilter(saturationFinal));
        //myFilter.addSubFilter(new ContrastSubFilter(toneFinal));
        //myFilter.addSubFilter(new ColorOverlaySubFilter(10, redFinal, greenFinal, buleFinal ));
//        myFilter.addSubFilter(new VignetteSubfilter(this
//                ,vintageFinal));
        finalImage = myFilter.processFilter(bitmap);
    }


    private void resetControls() {
        if (biBrightnessFragment != null) {
            biBrightnessFragment.resetControls();
        }
        if (saturationFragment != null) {
            saturationFragment.resetControls();
        }
        if (contrastFragment != null) {
            contrastFragment.resetControls();
        }
        brightnessFinal = 0;
        saturationFinal = 1.0f;
        contrastFinal = 1.0f;
    }

    @Override
    public void onFilterSelected(int position) {


        if (position == 3){
            startActivity(new Intent(MainActivity.this, CropActivity.class));
        }
        if (position> 3){
            viewPager.setCurrentItem(position -1);
        }
        if (position< 3){
            viewPager.setCurrentItem(position);
        }

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    // load the default image from assets on app launch
    private void loadImage(Bitmap bitmap) {
        originalImage = bitmap;
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        imagePreview.setImageBitmap(originalImage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_open) {
            openImageFromGallery();
            return true;
        }

        if (id == R.id.action_save) {
            saveImageToGallery();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void openImageFromGallery() {

    }

    private void saveImageToGallery() {

    }

    private void openImage(String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(path), "image/*");
        startActivity(intent);
    }
}
