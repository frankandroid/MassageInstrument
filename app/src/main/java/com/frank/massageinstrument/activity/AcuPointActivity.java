package com.frank.massageinstrument.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.frank.massageinstrument.R;
import com.frank.massageinstrument.adapter.MyStringRvAdapter;
import com.frank.massageinstrument.custom.SpaceItemDecoration;
import com.frank.massageinstrument.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 开穴
 * Created by huliuda on 18-7-25.
 */

public class AcuPointActivity extends BaseActivity implements MyStringRvAdapter.OnItemClick{

    @BindView(R.id.headerview)
    HeaderView headerview;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    MyStringRvAdapter mAdapter;
    private List<String> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaixue_zhengzhuang);
        ButterKnife.bind(this);

//        for(int i=0;i<20;i++) {
//            list.add("腰椎劳损");
//        }
        list.add("经穴");
        list.add("经外奇穴");
        list.add("其它穴位");
        list.add("耳穴");
        list.add("头穴");
        list.add("针灸技法");

        mRecycler.addItemDecoration(new SpaceItemDecoration(this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.normal_border)));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAnimation(null);
        mAdapter = new MyStringRvAdapter(list, true,this);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setHasFixedSize(true);
        mRecycler.setNestedScrollingEnabled(false);
        mAdapter.setOnItemClick(this);

        headerview.setOnClickListener(new HeaderView.OnClickListener() {
            @Override
            public void onBackClick() {
                AcuPointActivity.this.finish();
            }

            @Override
            public void onMainPage() {
                backToMainPage();
            }
        });

    }

    @Override
    public void onItemClick(String bean, int position) {
        Intent intent = new Intent(AcuPointActivity.this,AcupointListActivity.class);
        intent.putExtra("name",bean);
        startActivity(intent);
        Toast.makeText(AcuPointActivity.this, String.valueOf(position)+"."+bean, Toast.LENGTH_SHORT).show();
    }

}
