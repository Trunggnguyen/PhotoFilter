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
 * @author Trung
 * subfilter used to tweak brightness of the Bitmap
 */

public class VintageFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {


    private EditImageFragmentListener listener;


    @BindView(R.id.seekbar_brightness)
    SeekBar seekBarSaturation;

    @BindView(R.id.value)
    TextView textView;

    public void setListener(EditImageFragmentListener listener) {
        this.listener = listener;
    }

    public VintageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vintage, container, false);

        ButterKnife.bind(this, view);

        seekBarSaturation.setMax(200);
        seekBarSaturation.setProgress(0);
        seekBarSaturation.setOnSeekBarChangeListener(this);
        textView.setText("0");
        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (listener != null) {
            int floatVal =  progress;
            listener.onVintageChanged(floatVal);
            textView.setText(""+ progress);

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
        seekBarSaturation.setProgress(10);
    }
}

