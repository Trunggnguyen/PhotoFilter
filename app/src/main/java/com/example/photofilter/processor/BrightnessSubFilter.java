package com.example.photofilter.processor;

import android.graphics.Bitmap;
import com.zomato.photofilters.imageprocessors.ImageProcessor;
import com.zomato.photofilters.imageprocessors.SubFilter;


/**
 * @author Hieu
 * subfilter used to tweak brightness of the Bitmap
 */
public class BrightnessSubFilter implements SubFilter {
    private static String tag = "";
    // Value is in integer
    private int brightness = 0;


    public BrightnessSubFilter(int brightness) {
        this.brightness = brightness;
    }

    @Override
    public Bitmap process(Bitmap inputImage) {
        return ImageProcessor.doBrightness(brightness, inputImage);
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void setTag(Object tag) {
        BrightnessSubFilter.tag = (String) tag;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }


    public void changeBrightness(int value) {
        this.brightness += value;
    }

}
