package com.rhapsody.xhan.recyclerviewdemo.view;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xhan on 2/5/16.
 */
public class DemoAdapter extends RecyclerView.Adapter<ItemViewHolder> {
	List<String> items;

	public DemoAdapter(List<String> items) {
		this.items = items;
		if (this.items == null) {
			this.items = new LinkedList<>();
		}
	}

	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ItemViewHolder(new ItemView(parent.getContext()));
	}

	@Override
	public void onBindViewHolder(ItemViewHolder holder, int position) {
		String itemText = items.get(position);
		holder.itemText.setText(itemText);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public void add(String... newItems) {
		items.addAll(0, Arrays.asList(newItems));
	}

	public void insert(int index, String... newItems) {
		if (newItems == null || newItems.length == 0) {
			return;
		}

		int addCount = 0;
		for (String newItem : newItems) {
			items.add(index + addCount, newItem);
			addCount ++;
		}
	}

	public void remove(String... itemsToRemove) {
		items.removeAll(Arrays.asList(itemsToRemove));
	}

	public void removeAt(int index) {
		items.remove(index);
	}

	public void removeRange(int index, int count) {
		for (int i = index + count - 1; i >= index; i --) {
			items.remove(i);
		}
	}
}
