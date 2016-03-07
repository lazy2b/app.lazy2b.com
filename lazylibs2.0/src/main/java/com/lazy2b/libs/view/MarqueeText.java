/**
 * 项目名:CommSrc
 * 包  名:com.royaleu.hfl.view
 * 文件名:MarqueeText.java
 * 创  建:2015年12月24日上午10:11:20
 * Copyright © 2015, GDQL All Rights Reserved.
 */
package com.lazy2b.libs.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 类名: MarqueeText <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:Administrator
 * @version $Id: MarqueeText.java 38 2015-12-24 04:23:53Z lazy2b $
 */
public class MarqueeText extends TextView {

	public MarqueeText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MarqueeText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MarqueeText(Context context) {
		super(context);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		if (focused)
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		// TODO Auto-generated method stub
		if (hasWindowFocus)
			super.onWindowFocusChanged(hasWindowFocus);
	}

	@Override
	public boolean isFocused() {
		return true;
	}
}
