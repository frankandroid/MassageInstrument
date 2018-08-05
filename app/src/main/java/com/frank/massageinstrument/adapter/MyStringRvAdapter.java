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
import android.widget.TextView;


import com.frank.massageinstrument.R;

import java.util.List;


public class MyStringRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_TOP = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_END = 2;
    private static final int VIEW_TYPE_EMPTY = 3;

    private List<String> list;
    private Context context;
    private boolean enableIndex;

    public MyStringRvAdapter(@NonNull List<String> Tags, boolean enableIndex,
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
                .rv_string_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            String resultBean = (String) list
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
        private TextView tvContent;
        private View view;

        ItemHolder(View view) {
            super(view);
            this.view = view;
            tvContent = (TextView) view.findViewById(R.id.tv_content);
        }

        public void setData(final String resultBean, final int postion) {
            String index = "";
            if(enableIndex) {
                index = String.valueOf(postion+1)+".";
            }
            tvContent.setText(index+resultBean);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClick != null) {
                        onItemClick.onItemClick(resultBean,postion);
                    }
                }
            });
        }
    }

    private OnItemClick onItemClick;

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItemClick(String bean, int position);
    }

}
