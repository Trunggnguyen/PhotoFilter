package com.example.photofilter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photofilter.model.NavItem;
import com.example.photofilter.uitls.BitmapUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.androidhive.imagefilters.R;
/**
 * @author Trung
 * subfilter used to tweak brightness of the Bitmap
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    private List<String> photoItemList;
    private PhotoAdapterListener listener;
    private Context mContext;
    private int selectedIndex = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail)
        ImageView navIcon;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            navIcon = view.findViewById(R.id.thumbnail);
            cardView = view.findViewById(R.id.cardview_image);
            cardView.setCardBackgroundColor(R.drawable.imageview_radious);
        }
    }

    public PhotoAdapter(Context context, List<String> navItemList, PhotoAdapterListener listener) {
        mContext = context;
        this.photoItemList = navItemList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final String navlItem = photoItemList.get(position);
        Glide.with(mContext)
                .load(navlItem)
                .into(holder.navIcon);
        holder.itemView.setOnClickListener(view -> {
            listener.onFilterSelected(navlItem);

        });
    }

    @Override
    public int getItemCount() {
        return photoItemList.size();
    }


}
