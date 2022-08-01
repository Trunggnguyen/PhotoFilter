package com.example.photofilter.processor;

import android.graphics.Bitmap;
import com.zomato.photofilters.imageprocessors.ImageProcessor;
import com.zomato.photofilters.imageprocessors.SubFilter;


/**
 * @author Hieu
 * subfilter used to tweak brightness of the Bitmap
 */
public class ColorOverlaySubFilter implements SubFilter {
    private static String tag = "";

    // the color overlay depth is between 0-255
    private final int colorOverlayDepth;

    // these values are between 0-1
    private final float colorOverlayRed;
    private final float colorOverlayGreen;
    private final float colorOverlayBlue;


    public ColorOverlaySubFilter(int depth, float red, float green, float blue) {
        this.colorOverlayDepth = depth;
        this.colorOverlayRed = red;
        this.colorOverlayBlue = blue;
        this.colorOverlayGreen = green;
    }

    @Override
    public Bitmap process(Bitmap inputImage) {
        return ImageProcessor.doColorOverlay(
                colorOverlayDepth, colorOverlayRed, colorOverlayGreen, colorOverlayBlue, inputImage
        );
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void setTag(Object tag) {
        ColorOverlaySubFilter.tag = (String) tag;
    }
}
