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
 */
public class ColorFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {


    private EditImageFragmentListener listener;


    @BindView(R.id.seekbar_red)
    SeekBar seekBarread;

    @BindView(R.id.seekbar_green)
    SeekBar seekBargreen;

    @BindView(R.id.seekbar_blue)
    SeekBar seekBarblue;

    float read =0;
    float green = 0;
    float blue =0;
    public void setListener(EditImageFragmentListener listener) {
        this.listener = listener;
    }

    public ColorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color, container, false);

        ButterKnife.bind(this, view);

        seekBarread.setMax(10);
        seekBarread.setProgress(0);
        seekBarread.setOnSeekBarChangeListener(this);

        seekBarblue.setMax(10);
        seekBarblue.setProgress(0);
        seekBarblue.setOnSeekBarChangeListener(this);

        seekBargreen.setMax(10);
        seekBargreen.setProgress(0);
        seekBargreen.setOnSeekBarChangeListener(this);

        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        progress = progress -1;
        if (seekBar.getId() == R.id.seekbar_red) {
            read =  progress%10;
            listener.onColorChanged(read, green, blue);
        }

        if (seekBar.getId() == R.id.seekbar_green) {

            green =  progress%10;
            listener.onColorChanged(read, green, blue);
        }

        if (seekBar.getId() == R.id.seekbar_blue) {

            blue =  progress%10;
            listener.onColorChanged(read, green, blue);
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

}

