package com.example.photofilter.fragment;
/**
 * @author Trung
 */
public interface EditImageFragmentListener {
        void onBrightnessChanged(int brightness);

        void onSaturationChanged(float saturation);

        void onContrastChanged(float contrast);

        void onToneChanged(float tone);

        void onColorChanged(float read, float green, float blue);

        void onVintageChanged(int vintage);

        void onEditStarted();

        void onEditCompleted();
    }