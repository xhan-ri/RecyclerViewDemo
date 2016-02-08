package com.rhapsody.xhan.recyclerviewdemo.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rhapsody.xhan.recyclerviewdemo.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
	public TextView itemText;
	public ItemViewHolder(View itemView) {
		super(itemView);
		itemText = (TextView)itemView.findViewById(R.id.item_text);
	}
}
