package com.example.photofilter.adapter;

import com.zomato.photofilters.imageprocessors.Filter;
/**
 * @author Luong
 * subfilter used to tweak brightness of the Bitmap
 */
public interface ThumbnailsAdapterListener {
        void onFilterSelected(Filter filter);
    }