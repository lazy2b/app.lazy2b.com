package com.lazy2b.libs.utils;

import com.lazy2b.libs.interfaces.ILazyBase;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * SharedPreferences 数据管理
 * 
 * @author jack.lin <jack.lin@qq.com>
 * 
 * @version $Id: SharedPreferencesUtils.java 3 2015-10-28 03:27:15Z lazy2b $
 * 
 */
/**
 * 类名: SharedPreferencesUtils <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
public class SharedPreferencesUtils implements ILazyBase {

	private SharedPreferences mSharedPreferences;

	public SharedPreferencesUtils(Context context, String file) {
		mSharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
	}

	public SharedPreferences.Editor getEditor() {
		return mSharedPreferences.edit();
	}

	public String getString(String key, String defValue) {
		return mSharedPreferences.getString(key, defValue);
	}

	public Long getLong(String key, long defValue) {
		return mSharedPreferences.getLong(key, defValue);
	}

	public Float getFloat(String key, float defValue) {
		return mSharedPreferences.getFloat(key, defValue);
	}

	public Boolean getBoolean(String key, Boolean defValue) {
		return mSharedPreferences.getBoolean(key, defValue);
	}

	public Integer getInt(String key, Integer defValue) {
		return mSharedPreferences.getInt(key, defValue);
	}

	public void putInt(String key, Integer value) {
		mSharedPreferences.edit().putInt(key, value).commit();
	}

	public void putString(String key, String value) {
		mSharedPreferences.edit().putString(key, value).commit();
	}

	public void putLong(String key, long value) {
		mSharedPreferences.edit().putLong(key, value).commit();
	}

	public void putBoolean(String key, Boolean value) {
		mSharedPreferences.edit().putBoolean(key, value).commit();
	}

	public void putFloat(String key, float value) {
		mSharedPreferences.edit().putFloat(key, value).commit();
	}

	public void remove(String key) {
		try {
			mSharedPreferences.edit().remove(key).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
