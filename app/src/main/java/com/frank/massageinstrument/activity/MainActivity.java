package com.frank.massageinstrument.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


import com.frank.massageinstrument.R;
import com.frank.massageinstrument.db.DaoManager;
import com.frank.massageinstrument.entity.AcupointBean;
import com.frank.massageinstrument.view.ItemView;
import com.frank.massageinstrument.view.TimerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    GridView gridView;

    private List<View> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gv_item);
        initView();
    }

    private void initView(){
        TimerView timerView = new TimerView(this);
        timerView.setBackgroundColor(getResources().getColor(R.color.color1));
        setViewFullScreen(timerView);
        timerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
            }
        });
        list.add(timerView);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.main_page2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
            }
        });
        list.add(imageView);

        ItemView itemView = new ItemView(this);
        itemView.setIcon(R.mipmap.icon_binzheng);
        itemView.setText(getString(R.string.txt_binzheng));
        itemView.setBackgroundColor(getResources().getColor(R.color.color2));
        setViewFullScreen(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
            }
        });
        list.add(itemView);

        imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.main_page4);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "4", Toast.LENGTH_SHORT).show();
            }
        });
        list.add(imageView);

        itemView = new ItemView(this);
        itemView.setIcon(R.mipmap.icon_cure);
        itemView.setText(getString(R.string.txt_cure));
        itemView.setBackgroundColor(getResources().getColor(R.color.color3));
        setViewFullScreen(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "5", Toast.LENGTH_SHORT).show();
            }
        });
        list.add(itemView);

        itemView = new ItemView(this);
        itemView.setIcon(R.mipmap.icon_kaixue);
        itemView.setText(getString(R.string.txt_kaixue));
        itemView.setBackgroundColor(getResources().getColor(R.color.color4));
        setViewFullScreen(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AcuPointActivity.class));
//                Toast.makeText(MainActivity.this, "6", Toast.LENGTH_SHORT).show();
            }
        });
        list.add(itemView);

        itemView = new ItemView(this);
        itemView.setIcon(R.mipmap.icon_knowlage);
        itemView.setText(getString(R.string.txt_knowlage));
        itemView.setBackgroundColor(getResources().getColor(R.color.color5));
        setViewFullScreen(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "7", Toast.LENGTH_SHORT).show();
            }
        });
        list.add(itemView);

        itemView = new ItemView(this);
        itemView.setIcon(R.mipmap.icon_setting);
        itemView.setText(getString(R.string.txt_setting));
        itemView.setBackgroundColor(getResources().getColor(R.color.color6));
        setViewFullScreen(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "8", Toast.LENGTH_SHORT).show();
            }
        });
        list.add(itemView);

        itemView = new ItemView(this);
        itemView.setIcon(R.mipmap.icon_help);
        itemView.setText(getString(R.string.txt_help));
        itemView.setBackgroundColor(getResources().getColor(R.color.color7));
        setViewFullScreen(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "9", Toast.LENGTH_SHORT).show();
            }
        });
        list.add(itemView);

        MyAdapter myAdapter = new MyAdapter(list);
        gridView.setAdapter(myAdapter);
    }

    private void setViewFullScreen(View view) {
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(480,258);
        view.setLayoutParams(params);
    }

    class MyAdapter extends BaseAdapter {
        private List<View> list = new ArrayList<>();

        public MyAdapter(List<View> list) {
            super();
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }


        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            final View item = list.get(position);
            return item;
        }


    }
}
