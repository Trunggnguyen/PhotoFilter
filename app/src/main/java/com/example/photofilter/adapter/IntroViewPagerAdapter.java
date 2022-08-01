package com.example.photofilter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import info.androidhive.imagefilters.R;

import com.example.photofilter.model.ScreenIntroItem;


import java.util.List;
/**
 * @author Trung
 * subfilter used to tweak brightness of the Bitmap
 */
public class IntroViewPagerAdapter extends PagerAdapter {

    Context mContext ;
    List<ScreenIntroItem> mListScreen;

    public IntroViewPagerAdapter(Context mContext, List<ScreenIntroItem> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.item_intrroscreen,null);

        TextView intro_helo = layoutScreen.findViewById(R.id.intro_helo);
        TextView title = layoutScreen.findViewById(R.id.intro_title);
        TextView description = layoutScreen.findViewById(R.id.intro_description);

        ImageView view= layoutScreen.findViewById(R.id.place);



        intro_helo.setText(mListScreen.get(position).getHello());
        title.setText(mListScreen.get(position).getTitle());
        description.setText(mListScreen.get(position).getDescription());
        view.setImageResource(mListScreen.get(position).getScreenImg());




        container.addView(layoutScreen);

        return layoutScreen;

    }
    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);

    }
}
