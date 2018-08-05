package com.frank.massageinstrument.adapter;

/**
 * String实体的recylerview适配器
 * Created by huliuda on 18-6-12.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.frank.massageinstrument.R;
import com.frank.massageinstrument.adapter.bean.GridItemBean;

import java.util.List;


public class MyGridRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_TOP = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_END = 2;
    private static final int VIEW_TYPE_EMPTY = 3;

    private List<GridItemBean> list;
    private Context context;
    private boolean enableIndex;
    private int selectedPosition = 0;

    public MyGridRvAdapter(@NonNull List<GridItemBean> Tags, boolean enableIndex,
                           @NonNull Context context) {
        this.context = context;
        this.list = Tags;
        this.enableIndex = enableIndex;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.isEmpty()) {
            if (position == 0) {
                return VIEW_TYPE_EMPTY;
            }
            return VIEW_TYPE_END;
        }
        if (position == 0) {
            return VIEW_TYPE_TOP;
        } else if (position == getItemCount() - 1) {
            return VIEW_TYPE_END;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .rv_grid_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            GridItemBean resultBean = list
                    .get(position);
            ItemHolder goodsTagItemHolder = (ItemHolder) holder;
            goodsTagItemHolder.setData(resultBean,position);
        }
    }

    @Override
    public int getItemCount() {
        if (list.isEmpty()) {
            return 0;
        }
        return list.size();
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageButton ibSwitch;
        private ImageButton ibSetting;
        private TextView tvName;

        ItemHolder(View view) {
            super(view);
            this.view = view;
            ibSwitch = view.findViewById(R.id.btn_switch);
            ibSetting = view.findViewById(R.id.btn_setting);
            tvName = view.findViewById(R.id.tv_name);
        }

        public void setData(final GridItemBean resultBean,final int postion) {
            tvName.setText(resultBean.getName());
            if(resultBean.isSwitchOn()) {
                ibSwitch.setTag("1");
            } else {
                ibSwitch.setTag("0");
            }
            if(selectedPosition == postion) {
                view.setSelected(true);
            } else {
                view.setSelected(false);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!view.isSelected()) {
                        selectedPosition = postion;
                        if(onItemSelectedListener != null) {
                            onItemSelectedListener.onItemSelected(resultBean,postion,!view.isSelected());
                        }
                        notifyDataSetChanged();
                    }
                }
            });

            ibSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tag = (String) view.getTag();
                    boolean bSelected = false;
                    if(tag.equals("1")) {
                        ibSwitch.setBackgroundResource(R.drawable.btn_switch_off);
                        bSelected = false;
                        view.setTag("0");
                    } else {
                        ibSwitch.setBackgroundResource(R.drawable.btn_switch_on);
                        view.setTag("1");
                        bSelected = true;
                    }
//                    view.setSelected(!view.isSelected());
                    if(onSwitchSelectedListener != null) {
                        onSwitchSelectedListener.onSwitchClick(resultBean,postion,bSelected);
                    }
                }
            });

            ibSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onSwitchSelectedListener != null) {
                        onSwitchSelectedListener.onSettingClick(resultBean,postion);
                    }
                }
            });
        }
    }

    private OnItemSelectedListener onItemSelectedListener;
    private OnClickListener onSwitchSelectedListener;

    public OnItemSelectedListener getOnItemSelectedListener() {
        return onItemSelectedListener;
    }

    public OnClickListener getOnSwitchSelectedListener() {
        return onSwitchSelectedListener;
    }

    public void setOnSwitchSelectedListener(OnClickListener onSwitchSelectedListener) {
        this.onSwitchSelectedListener = onSwitchSelectedListener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(GridItemBean bean, int position, boolean bSelected);
    }

    public interface OnClickListener {
        void onSwitchClick(GridItemBean bean, int position, boolean bSelected);
        void onSettingClick(GridItemBean bean, int position);
    }

}
