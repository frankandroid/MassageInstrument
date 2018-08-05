package com.frank.massageinstrument.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.frank.massageinstrument.R;
import com.frank.massageinstrument.adapter.MyGridRvAdapter;
import com.frank.massageinstrument.adapter.bean.GridItemBean;
import com.frank.massageinstrument.custom.GridSpacingItemDecoration;
import com.frank.massageinstrument.db.DaoManager;
import com.frank.massageinstrument.entity.AcupointParamBean;
import com.frank.massageinstrument.view.HeaderView;
import com.frank.massageinstrument.view.MySeekbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 开穴
 * Created by huliuda on 18-7-25.
 */

public class AcuPointSettingActivity extends BaseActivity implements MyGridRvAdapter.OnItemSelectedListener,MyGridRvAdapter.OnClickListener {

    @BindView(R.id.headerview)
    HeaderView headerview;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_remainder_min)
    TextView tvRemainderMin;
    @BindView(R.id.tv_remainder_sec)
    TextView tvRemainderSec;
    @BindView(R.id.myseekbar)
    MySeekbar seekbar;
    @BindView(R.id.myseekbar2)
    MySeekbar seekbar2;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    List<GridItemBean> list = new ArrayList<>();
    MyGridRvAdapter mAdapter;

    private String name;
    private String explain;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acupoint_setting);
        ButterKnife.bind(this);

        headerview.setOnClickListener(new HeaderView.OnClickListener() {
            @Override
            public void onBackClick() {
                AcuPointSettingActivity.this.finish();
            }

            @Override
            public void onMainPage() {
                backToMainPage();
            }
        });

        name = getIntent().getStringExtra("name");
        explain = getIntent().getStringExtra("explain");

        tvTitle.setText(name);
        tvRemainderMin.setText("05");
        tvRemainderSec.setText("50");

        seekbar.setTitle("频率");
        seekbar.setMax(15);

        seekbar2.setTitle("强度");
        seekbar2.setMax(15);

        String nn = "太冲";
        for(int i=0;i<20;i++){
            GridItemBean itemBean = new GridItemBean(nn,false);
            list.add(itemBean);
        }

        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(4,20,false);
        mRecycler.addItemDecoration(gridSpacingItemDecoration);
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAnimation(null);
        mAdapter = new MyGridRvAdapter(list, true,this);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setHasFixedSize(true);
        mRecycler.setNestedScrollingEnabled(false);
        mAdapter.setOnItemSelectedListener(this);
        mAdapter.setOnSwitchSelectedListener(this);
    }

    @Override
    public void onItemSelected(GridItemBean bean, int position, boolean bSelected) {
        AcupointParamBean acupointParamBean = DaoManager.getInstance().queryAcupointParamByName(name);
        if(acupointParamBean != null) {
            seekbar.setProgress(acupointParamBean.getFrequnce());
            seekbar2.setProgress(acupointParamBean.getStrength());
        }
    }

    @Override
    public void onSwitchClick(GridItemBean bean, int position, boolean bSelected) {

    }

    @Override
    public void onSettingClick(GridItemBean bean, int position) {
        Intent intent = new Intent(AcuPointSettingActivity.this,AcuPointSettingDetailActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("explain",explain);
        startActivity(intent);
    }
}
