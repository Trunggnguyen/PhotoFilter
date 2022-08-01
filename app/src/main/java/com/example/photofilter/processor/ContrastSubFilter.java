package com.example.photofilter.processor;

import android.graphics.Bitmap;

import com.zomato.photofilters.imageprocessors.ImageProcessor;
import com.zomato.photofilters.imageprocessors.SubFilter;


/**
 * @author Hieu
 * subfilter used to tweak brightness of the Bitmap
 */
public class ContrastSubFilter implements SubFilter {

    private static String tag = "";

    // The value is in fraction, value 1 has no effect
    private float contrast = 0;

    public ContrastSubFilter(float contrast) {
        this.contrast = contrast;
    }

    @Override
    public Bitmap process(Bitmap inputImage) {
        return ImageProcessor.doContrast(contrast, inputImage);
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void setTag(Object tag) {
        ContrastSubFilter.tag = (String) tag;
    }

    /**
     * Sets the contrast value by the value passed in as parameter
     */
    public void setContrast(float contrast) {
        this.contrast = contrast;
    }

    /**
     * Changes contrast value by the value passed in as a parameter
     */
    public void changeContrast(float value) {
        this.contrast += value;
    }
}
