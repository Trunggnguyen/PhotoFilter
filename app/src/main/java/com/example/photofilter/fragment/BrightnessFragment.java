package com.example.photofilter.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.androidhive.imagefilters.R;

/**
 * @author Hieu
 */
public class BrightnessFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private EditImageFragmentListener listener;

    @BindView(R.id.seekbar_brightness)
    SeekBar seekBarBrightness;

    @BindView(R.id.value)
    TextView textView;


    public void setListener(EditImageFragmentListener listener) {
        this.listener = listener;
    }

    public BrightnessFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brightness, container, false);

        ButterKnife.bind(this, view);
        seekBarBrightness.setMax(200);
        seekBarBrightness.setProgress(100);
        textView.setText("0");
        seekBarBrightness.setOnSeekBarChangeListener(this);
        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (listener != null) {
                listener.onBrightnessChanged(progress - 100);
                textView.setText(""+ (progress - 100));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (listener != null)
            listener.onEditStarted();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (listener != null)
            listener.onEditCompleted();
    }

    public void resetControls() {
        seekBarBrightness.setProgress(100);
    }


}
