package com.lazy2b.libs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class ChildGridView extends GridView {
	public ChildGridView(Context context) {
		super(context);
	}

	public ChildGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ChildGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// 不出现滚动条
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
