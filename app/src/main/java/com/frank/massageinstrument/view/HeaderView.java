package com.frank.massageinstrument.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.frank.massageinstrument.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * author : huliuda
 * date : 2018/07/22 16:33
 */

public class HeaderView extends RelativeLayout implements View.OnClickListener {
    private final static int MSG_UPDATE_CURRENT_TIME = 1;
    private OnClickListener onClickListener;

    MyHandler myHandler = null;

    ImageButton btnBack;
    ImageButton btnMainPage;
    TextView tvTime;
    TextView tvTime2;
    TextView tvArea;
    TextView tvDimension;

    public HeaderView(Context context) {
        super(context, null);
        initData(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_header, this);
        btnBack = (ImageButton) view.findViewById(R.id.btn_back);
        btnMainPage = (ImageButton) view.findViewById(R.id.btn_main_page);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvTime2 = (TextView) view.findViewById(R.id.tv_time2);
        tvArea = (TextView) view.findViewById(R.id.tv_area);
        tvDimension = (TextView) view.findViewById(R.id.tv_dimension);

        tvArea.setText("当前区域：深圳");
        tvDimension.setText("维度：113");

        btnBack.setOnClickListener(this);
        btnMainPage.setOnClickListener(this);

        myHandler = new MyHandler();
        myHandler.sendEmptyMessage(MSG_UPDATE_CURRENT_TIME);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        myHandler.removeMessages(MSG_UPDATE_CURRENT_TIME);
        myHandler = null;
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                if (onClickListener != null) {
                    onClickListener.onBackClick();
                }
                break;
            case R.id.btn_main_page:
                if (onClickListener != null) {
                    onClickListener.onMainPage();
                }
                break;
        }
    }

    private class MyHandler extends Handler {

        public MyHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_CURRENT_TIME:
                    updateCurrentTime();
                    sendEmptyMessageDelayed(MSG_UPDATE_CURRENT_TIME, 500);
                    break;
                default:
                    break;
            }
        }
    }

    private void updateCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        String date = dateFormat.format(curDate);
        tvTime.setText("背景时间：" + date);
        tvTime2.setText("真太阳时：" + date);
    }

    public interface OnClickListener {
        void onBackClick();

        void onMainPage();
    }
}
