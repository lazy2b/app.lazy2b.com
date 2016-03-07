package com.lazy2b.libs.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.view.BaseViewHolder;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 类名: BaseLvAdapter <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
public abstract class BaseLvAdapter<T> extends BaseAdapter implements ILazyBase {

	protected List<T> mList = null;

	protected Handler mUIHandler = null;

	protected Context mCxt = null;

	protected LayoutInflater mInflater = null;

	protected int mContentViewResId = android.R.layout.activity_list_item;

	public BaseLvAdapter() {
	}

	public BaseLvAdapter(Context _cxt, List<T> _list, Handler _handler) {

		mCxt = _cxt;
		mUIHandler = _handler;
		setList(_list);

	}

	public BaseLvAdapter(Context _cxt, List<T> _list, Handler _handler,
			int _resId) {

		this(_cxt, _list, _handler);
		mContentViewResId = _resId;
		mInflater = LayoutInflater.from(_cxt);

	}

	public void addList(List<T> _list) {

		if (mList == null) {
			mList = new ArrayList<T>();
		}

		if (_list != null) {
			mList.addAll(_list);
		}

	}

	public List<T> getList() {
		return mList;
	}

	public void setList(List<T> _list) {

		if (mList == null) {
			mList = new ArrayList<T>();
		}

		if (_list != null) {
			mList.clear();
			mList.addAll(_list);
		}

	}

	@Override
	public int getCount() {

		try {
			return mList.size();
		} catch (Exception e) {
			return 0;
		}

	}

	@Override
	public T getItem(int position) {

		try {
			return mList.get(position);
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public long getItemId(int position) {

		return position;

	}

	/**
	 * @param view
	 * @return
	 */
	private BaseLvHolder getHolder(View view, Class<? extends BaseLvHolder> cls) {
		BaseLvHolder holder = getHolder();
		if (holder != null) {
			ButterKnife.bind(holder, view);
		} else {
			holder = (BaseLvHolder) BaseViewHolder.create(mCxt, view, cls);
		}
		return holder;
	}

	protected BaseLvHolder getHolder() {
		return null;
	}

	protected Class<? extends BaseLvHolder> getHolderClass() {
		return BaseLvHolder.class;
	}

	protected abstract void handleView(BaseLvHolder _holder);

	/**
	 * Holder中需要一次性设置的控件初始化;<br/>
	 * 如手动设置大小，字体颜色等等
	 * 
	 * @param _holder
	 */
	protected void initView(BaseLvHolder _holder) {
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		BaseLvHolder holder;

		if (view == null) {

			try {
				view = mInflater.inflate(mContentViewResId, null);
			} catch (Exception e) {

				LogUtils.i(e.getMessage());

			}

			holder = getHolder(view, getHolderClass());

			holder.position = position;

			initView(holder);

			view.setTag(mContentViewResId, holder);

		} else {

			holder = (BaseLvHolder) view.getTag(mContentViewResId);

			holder.position = position; 

		}

		handleView(holder);

		return view;

	}

	public static class BaseLvHolder extends BaseViewHolder {
		public int position;

		@Override
		public void fill(Object... args) {
		}
	}
}
