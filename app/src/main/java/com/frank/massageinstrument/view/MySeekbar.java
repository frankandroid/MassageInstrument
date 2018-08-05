package com.frank.massageinstrument.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.frank.massageinstrument.R;


/**
 * author : huliuda
 * date : 2018/07/22 16:33
 */

public class MySeekbar extends RelativeLayout {
    TextView tvTile;
    TextView tvValue;
    SeekBar seekBar;

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener;

    public MySeekbar(Context context) {
        super(context, null);
        initData(context);
    }

    public MySeekbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_seekbar, this);
        tvTile = (TextView) view.findViewById(R.id.tv_title);
        tvValue = (TextView) view.findViewById(R.id.tv_value);
        tvValue.setText("00");
        seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvValue.setText(String.format("%02d", i));
                if(onSeekBarChangeListener != null) {
                    onSeekBarChangeListener.onProgressChanged(seekBar,i,b);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(onSeekBarChangeListener != null) {
                    onSeekBarChangeListener.onStartTrackingTouch(seekBar);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(onSeekBarChangeListener != null) {
                    onSeekBarChangeListener.onStopTrackingTouch(seekBar);
                }
            }
        });
    }

    public void setMax(int max) {
        seekBar.setMax(max);
    }

    public void setProgress(int progress){
        seekBar.setProgress(progress);
    }

    public void setTitle(String title){
        tvTile.setText(title);
    }

//    public void setValue(int value) {
//        tvValue.setText(String.valueOf(value));
//    }

    public int getValue() {
        String value = tvValue.getText().toString();
        if(TextUtils.isEmpty(value)) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.onSeekBarChangeListener = onSeekBarChangeListener;
    }
}
