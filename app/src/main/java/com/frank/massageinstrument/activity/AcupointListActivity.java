package com.frank.massageinstrument.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.frank.massageinstrument.R;
import com.frank.massageinstrument.adapter.MyStringRvAdapter;
import com.frank.massageinstrument.custom.SpaceItemDecoration;
import com.frank.massageinstrument.db.DaoManager;
import com.frank.massageinstrument.entity.AcupointBean;
import com.frank.massageinstrument.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

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

public class AcupointListActivity extends BaseActivity implements MyStringRvAdapter.OnItemClick {

    MyStringRvAdapter mAdapter;
    @BindView(R.id.headerview)
    HeaderView headerview;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.btn_search)
    ImageButton btnSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    private List<String> list = new ArrayList<>();
    private String name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acupoint_list);
        ButterKnife.bind(this);

        mRecycler.addItemDecoration(new SpaceItemDecoration(this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.normal_border)));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAnimation(null);
        mAdapter = new MyStringRvAdapter(list, true, this);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setHasFixedSize(true);
        mRecycler.setNestedScrollingEnabled(false);
        mAdapter.setOnItemClick(this);

        headerview.setOnClickListener(new HeaderView.OnClickListener() {
            @Override
            public void onBackClick() {
                AcupointListActivity.this.finish();
            }

            @Override
            public void onMainPage() {
                backToMainPage();
            }
        });

        name = getIntent().getStringExtra("name");
        if (TextUtils.isEmpty(name))
            return;
        tvTitle.setText(name);
        if ("经穴".equals(name)) {
            Observable.create(new Observable.OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    List<AcupointBean> acupointBeanList = DaoManager.getInstance().queryAcupoints();
                    for (AcupointBean bean : acupointBeanList) {
                        list.add(bean.getAcu_name());
                    }
                    subscriber.onNext(null);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
                @Override
                public void call(Object o) {
                    mAdapter.notifyDataSetChanged();
                }
            });
        } else {
            for (int i = 0; i < 20; i++) {
                list.add("快给数据");
            }
        }
    }

    @OnClick
    public void onClick(View view) {
        if ("经穴".equals(name)) {
            Observable.create(new Observable.OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    String mes = etSearch.getText().toString().trim();
                    List<AcupointBean> acupointBeanList;
                    if(TextUtils.isEmpty(mes)) {
                        acupointBeanList = DaoManager.getInstance().queryAcupoints();
                    } else {
                        acupointBeanList = DaoManager.getInstance().queryAcupointLikeName(mes);
                    }
                    list.clear();
                    for (AcupointBean bean : acupointBeanList) {
                        list.add(bean.getAcu_name());
                    }
                    subscriber.onNext(null);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
                @Override
                public void call(Object o) {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onItemClick(String bean, int position) {
        Intent intent = new Intent(AcupointListActivity.this, AcuPointExplainActivity.class);
        intent.putExtra("name", bean);
        startActivity(intent);
    }

}
