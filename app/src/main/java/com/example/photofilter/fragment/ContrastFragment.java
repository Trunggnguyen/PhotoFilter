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
public class ContrastFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private EditImageFragmentListener listener;


    @BindView(R.id.seekbar_contrast)
    SeekBar seekBarContrast;

    @BindView(R.id.value)
    TextView textView;
    public void setListener(EditImageFragmentListener listener) {
        this.listener = listener;
    }

    public ContrastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contrast, container, false);

        ButterKnife.bind(this, view);

        seekBarContrast.setMax(20);
        seekBarContrast.setProgress(0);

        textView.setText("0");

        seekBarContrast.setOnSeekBarChangeListener(this);

        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (listener != null) {
                progress += 10;
                float floatVal = .10f * progress;
                listener.onContrastChanged(floatVal);
            textView.setText(""+ (progress -10));
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
        seekBarContrast.setProgress(0);
    }


}
