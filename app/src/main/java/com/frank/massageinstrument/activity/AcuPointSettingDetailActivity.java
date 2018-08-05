package com.frank.massageinstrument.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.frank.massageinstrument.R;
import com.frank.massageinstrument.db.DaoManager;
import com.frank.massageinstrument.entity.AcupointParamBean;
import com.frank.massageinstrument.view.HeaderView;
import com.frank.massageinstrument.view.MySeekbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 开穴
 * Created by huliuda on 18-7-25.
 */

public class AcuPointSettingDetailActivity extends BaseActivity {

    @BindView(R.id.headerview)
    HeaderView headerview;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sb_frequency)
    MySeekbar sbFrequency;
    @BindView(R.id.sb_strength)
    MySeekbar sbStrength;
    @BindView(R.id.rd_mode1)
    RadioButton rdMode1;
    @BindView(R.id.rd_mode2)
    RadioButton rdMode2;
    @BindView(R.id.rd_mode3)
    RadioButton rdMode3;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.btn_save)
    ImageButton btnSave;
    @BindView(R.id.tv_explain_title)
    TextView tvExplainTitle;
    @BindView(R.id.tv_explain)
    TextView tvExplain;

    private String name;
    private String explain;
    private AcupointParamBean mAcupointParamBean;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acupoint_setting_detail);
        ButterKnife.bind(this);

        name = getIntent().getStringExtra("name");
        explain = getIntent().getStringExtra("explain");

        headerview.setOnClickListener(new HeaderView.OnClickListener() {
            @Override
            public void onBackClick() {
                AcuPointSettingDetailActivity.this.finish();
            }

            @Override
            public void onMainPage() {
                backToMainPage();
            }
        });

        sbFrequency.setMax(15);
        sbFrequency.setTitle("频率");
        sbStrength.setMax(15);
        sbStrength.setTitle("强度");

        rdMode1.setChecked(true);

        tvStartTime.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvEndTime.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvExplain.setText(explain);

        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                mAcupointParamBean = DaoManager.getInstance().queryAcupointParamByName(name);
                subscriber.onNext(null);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if(mAcupointParamBean != null) {
                    tvTitle.setText("电极编号：" + mAcupointParamBean.getNumber() + "  " + "穴位：" + mAcupointParamBean.getName());
                    sbFrequency.setProgress(mAcupointParamBean.getFrequnce());
                    sbStrength.setProgress(mAcupointParamBean.getStrength());
                    int mode = mAcupointParamBean.getMode();
                    if (mode == 0) {
                        rdMode1.setChecked(true);
                    } else if (mode == 1) {
                        rdMode2.setChecked(true);
                    } else if (mode == 2) {
                        rdMode3.setChecked(true);
                    }
                    tvStartTime.setText(mAcupointParamBean.getStartTime());
                    tvEndTime.setText(mAcupointParamBean.getEndTime());
                    tvExplain.setText(explain);
                } else {
                    tvTitle.setText("电极编号：未知  穴位：未知");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                    Date curDate = new Date(System.currentTimeMillis());
                    String time = dateFormat.format(curDate);
                    tvStartTime.setText(time);
                    tvEndTime.setText(time);
                }
            }
        });
    }

    @OnClick({R.id.btn_save,R.id.tv_start_time,R.id.tv_end_time})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.btn_save:
                if(mAcupointParamBean == null) {
                    mAcupointParamBean = new AcupointParamBean();
                }
                mAcupointParamBean.setEndTime(tvEndTime.getText().toString());
                mAcupointParamBean.setStartTime(tvStartTime.getText().toString());
                mAcupointParamBean.setFrequnce(sbFrequency.getValue());
                mAcupointParamBean.setStrength(sbStrength.getValue());
                mAcupointParamBean.setStatus(0);
                if(rdMode1.isChecked()) {
                    mAcupointParamBean.setMode(0);
                } else if(rdMode2.isChecked()) {
                    mAcupointParamBean.setMode(1);
                } else if(rdMode3.isChecked()) {
                    mAcupointParamBean.setMode(2);
                }
                mAcupointParamBean.setName(name);
                mAcupointParamBean.setNumber("01");//电极编号怎么来的，后面看看
                DaoManager.getInstance().insertAcupointParam(mAcupointParamBean);
                Toast.makeText(this,"已保存", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_start_time:
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                        String time = dateFormat.format(date);
                        tvStartTime.setText(time);
                    }
                }).build();
                pvTime.setDate(Calendar.getInstance());
                pvTime.show();
                break;
            case R.id.tv_end_time:
                //时间选择器
                TimePickerView pvTime2 = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                        String time = dateFormat.format(date);
                        tvEndTime.setText(time);
                    }
                }).build();
                pvTime2.setDate(Calendar.getInstance());
                pvTime2.show();
                break;
        }

    }

}
