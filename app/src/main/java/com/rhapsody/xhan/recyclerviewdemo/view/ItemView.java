package com.rhapsody.xhan.recyclerviewdemo.view;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rhapsody.xhan.recyclerviewdemo.R;

public class ItemView extends PercentRelativeLayout {
	private static final String TAG = "ItemView";
	TextView textView;
	public ItemView(Context context) {
		super(context);
		init();
	}

	public ItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.list_item, this, true);
		textView = (TextView) findViewById(R.id.item_text);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		Log.i(TAG, "onLayout: " + (textView == null ? "null" : textView.getText()));
	}
}
