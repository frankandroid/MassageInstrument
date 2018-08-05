package com.frank.massageinstrument.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frank.massageinstrument.R;


/**
 * 时间空间
 * author : huliuda
 * date : 2018/07/22 16:33
 */

public class ItemView extends LinearLayout {
    TextView tvText;
    ImageView imageView;

    public ItemView(Context context) {
        super(context,null);
        initData(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_main_item, this);
        tvText = (TextView)  view.findViewById(R.id.tv_text);
        imageView = (ImageView) view.findViewById(R.id.iv_icon);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setText(String text){
        tvText.setText(text);
    }

    public void setIcon(int resId){
        imageView.setImageResource(resId);
    }

}
