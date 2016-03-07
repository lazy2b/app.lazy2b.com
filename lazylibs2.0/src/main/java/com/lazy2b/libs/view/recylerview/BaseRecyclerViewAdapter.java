package com.lazy2b.libs.view.recylerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/24.
 */
@EBean
public abstract class BaseRecyclerViewAdapter<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public interface IRecyclerViewBindHandler<T extends Object> {
        /**
         * 邦定列表项
         *
         * @param item     对象
         * @param position 索引
         */
        void bind(T item, int position);
    }

    public interface IRecyclerViewBindHandler2<T extends Object> {
        /**
         * 邦定列表项
         *
         * @param item     对象
         * @param position 索引
         */
        void bind(T item, int position, int viewType);
    }

    @RootContext
    protected Context mCxt;

    protected OnRecyclerViewItemClickListener mOnItemClickListener;

    protected OnRecyclerViewItemLongClickListener mOnItemLongClickListener;

    protected List<T> mList = new ArrayList<>();

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<V>(onCreateItemView(parent, viewType));
    }

    @Override
    public void onBindViewHolder(final ViewWrapper<V> viewHolder, final int position) {
        V view = viewHolder.getView();
//        viewHolder.getAdapterPosition();
        final T item = getItem(position);
        view.setTag(item);

        //如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, position);
                }
            });

        }

        if (mOnItemLongClickListener != null) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return mOnItemLongClickListener.onItemLongClick(view, position);
                }
            });
        }


        if (view instanceof IRecyclerViewBindHandler2) {
            ((IRecyclerViewBindHandler2<T>) view).bind(item, position, getItemViewType(position));
        } else if (view instanceof IRecyclerViewBindHandler) {
            ((IRecyclerViewBindHandler<T>) view).bind(item, position);
        }
    }

    // additional methods to manipulate the items
}