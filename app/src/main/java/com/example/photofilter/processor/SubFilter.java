package com.example.photofilter.processor;

import android.graphics.Bitmap;

/**
 * @author Hieu
 * subfilter used to tweak brightness of the Bitmap
 */
public interface SubFilter {
    Bitmap process(Bitmap inputImage);

    Object getTag();

    void setTag(Object tag);
}
