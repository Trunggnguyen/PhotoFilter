package com.example.photofilter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photofilter.model.NavItem;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import info.androidhive.imagefilters.R;
/**
 * @author Trung
 * subfilter used to tweak brightness of the Bitmap
 */
public class NavAdapter extends RecyclerView.Adapter<NavAdapter.MyViewHolder> {

    private List<NavItem> navItemList;
    private NavAdapterListener listener;
    private Context mContext;
    private int selectedIndex = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nav_icon)
        ImageView navIcon;

        @BindView(R.id.nav_name)
        TextView navName;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public NavAdapter(Context context, List<NavItem> navItemList, NavAdapterListener listener) {
        mContext = context;
        this.navItemList = navItemList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nav, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final NavItem navlItem = navItemList.get(position);

        holder.navIcon.setImageResource(navlItem.getNavIcon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFilterSelected(position);
                selectedIndex = position;
                notifyDataSetChanged();
            }
        });

        holder.navName.setText(navlItem.getNavName());

        if (selectedIndex == position) {
            holder.navName.setTextColor(ContextCompat.getColor(mContext, R.color.purple_700));
            holder.navIcon.setColorFilter(mContext.getResources().getColor(R.color.purple_700), PorterDuff.Mode.SRC_IN);
        } else {
            holder.navName.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.navIcon.setColorFilter(mContext.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public int getItemCount() {
        return navItemList.size();
    }


}
