package com.frank.massageinstrument.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.frank.massageinstrument.R;
import com.frank.massageinstrument.db.DaoManager;
import com.frank.massageinstrument.entity.AcupointBean;
import com.frank.massageinstrument.view.HeaderView;

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

public class AcuPointExplainActivity extends BaseActivity {

    @BindView(R.id.headerview)
    HeaderView headerview;
    @BindView(R.id.tv_explain_title)
    TextView tvExplainTitle;
    @BindView(R.id.tv_explain)
    TextView tvExplain;

    AcupointBean mAcupointBean;
    String name;
    String explain;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acupoint_explain);
        ButterKnife.bind(this);

        name = getIntent().getStringExtra("name");

        headerview.setOnClickListener(new HeaderView.OnClickListener() {
            @Override
            public void onBackClick() {
                AcuPointExplainActivity.this.finish();
            }

            @Override
            public void onMainPage() {
                backToMainPage();
            }
        });

        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                mAcupointBean = DaoManager.getInstance().queryAcupointByName(name);
                subscriber.onNext(null);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if(mAcupointBean != null) {
                    tvExplainTitle.setText(name + "：");
                    tvExplain.setText(mAcupointBean.getAcu_xml());
                }
            }
        });

    }

    @OnClick({R.id.btn_zhiliao,R.id.btn_xueweitu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_xueweitu:
                break;
            case R.id.btn_zhiliao:
                Intent intent = new Intent(AcuPointExplainActivity.this,AcuPointSettingActivity.class);
                intent.putExtra("explain",mAcupointBean.getAcu_xml());
                intent.putExtra("name",name);
                startActivity(intent);
                break;
        }
    }

}
