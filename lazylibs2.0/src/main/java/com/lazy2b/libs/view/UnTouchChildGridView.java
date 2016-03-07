/**
 * 项目名:BetNotice
 * 包  名:com.royaleu.BetNotice.view
 * 文件名:LocalGridView.java
 * 创  建:2015年10月12日下午5:39:03
 * Copyright © 2015, GDQL All Rights Reserved.
 */
package com.lazy2b.libs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 类名: LocalGridView <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:linjp@ql.com
 * @version $Id: UnTouchChildGridView.java 27 2015-12-02 10:19:52Z lazy2b $
 */
public class UnTouchChildGridView extends ChildGridView {

	public UnTouchChildGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public UnTouchChildGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public UnTouchChildGridView(Context context) {
		super(context);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if (ev.getAction() == MotionEvent.ACTION_MOVE) {

			return true; // 禁止GridView滑动

		}

		return super.dispatchTouchEvent(ev);

	}

}
