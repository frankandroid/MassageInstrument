package com.frank.massageinstrument.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frank.massageinstrument.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 时间空间
 * author : huliuda
 * date : 2018/07/22 16:33
 */

public class TimerView extends RelativeLayout {
    private final static int MSG_UPDATE_CURRENT_TIME = 1;
    MyHandler myHandler = null;

    TextView tvHour;
    TextView tvYear;
    TextView tvDay;

    public TimerView(Context context) {
        super(context,null);
        initData(context);
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_timer, this);
        tvDay = (TextView)  view.findViewById(R.id.tv_day);
        tvYear = (TextView) view.findViewById(R.id.tv_year);
        tvHour = (TextView) view.findViewById(R.id.tv_hour);
        myHandler = new MyHandler();
        myHandler.sendEmptyMessage(MSG_UPDATE_CURRENT_TIME);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        myHandler.removeMessages(MSG_UPDATE_CURRENT_TIME);
        myHandler = null;
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
        SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy/MM");
        SimpleDateFormat dayDateFormat = new SimpleDateFormat("dd");
        SimpleDateFormat hourDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String year = yearDateFormat.format(curDate);
        String day = dayDateFormat.format(curDate);
        String hour = hourDateFormat.format(curDate);
        tvYear.setText(year);
        tvDay.setText(day);
        tvHour.setText(hour);
    }
}
