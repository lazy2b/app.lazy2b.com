package com.lazy2b.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.lazy2b.app.luzhu.LuZhuDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: app.lazy2b.com
 * 包 名: com.lazy2b.app
 * Copyright © 2017, CAIMAO.COM All Rights Reserved.
 * $Id$
 */

public class LuZhuAdapter<T extends LuZhuDataModel> extends RecyclerView.Adapter {

    public static final int LUZHU_TITLE_VIEW_TYPE = 0;
    public static final int LUZHU_RESULT_VIEW_TYPE = 1;

    LayoutInflater mInflater;
    Context mCxt;

    List<T> mList;

    public LuZhuAdapter(Context context, List<T> list) {
        mCxt = context;
        mList = list;
        mInflater = LayoutInflater.from(mCxt);
    }

    public void setList(List<T> list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public static class TitleHolder extends RecyclerView.ViewHolder {

        protected TextView mTv;

        public void fill(LuZhuDataModel item) {
//            if (item.most != -1) {
//                ((LuZhuTextView) mTv).setMost(item.most);
//            }
            mTv.setText(item.context);
        }

        public TitleHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView;
        }
    }

//    public static class ResHolder extends TitleHolder {
//
//        public ResHolder(View itemView) {
//            super(itemView);
//            mTv = (TextView) itemView;
//        }
//    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TitleHolder(viewType == LuZhuAdapter.LUZHU_TITLE_VIEW_TYPE ?
                mInflater.inflate(R.layout.item_luzhu_rv_title, null) :
                mInflater.inflate(R.layout.item_luzhu_rv_result, null));
//                new LuZhuTitleBarTextView(mCxt) : new LuZhuTextView(mCxt));
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder _holder, int position) {
        ((TitleHolder) _holder).fill(getItem(position));
    }

    public int getItemCount() {
        return mList.size();
    }
}
