package com.rhapsody.xhan.recyclerviewdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;

import com.rhapsody.xhan.recyclerviewdemo.view.DemoAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

	RecyclerView recyclerView;
	private static final int MIN_SIZE = 2;
	private static final int MAX_SIZE = 10;
	private static final String STRING_BASE = "0123456789";
	static Random random = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				recyclerView.setAdapter(new DemoAdapter(generateDemoItems(50)));
				recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this) {
					@Override
					public boolean supportsPredictiveItemAnimations() {
						CheckBox supportPredictiveAnimationCheck = (CheckBox)findViewById(R.id.support_predictive_animation);
						return supportPredictiveAnimationCheck.isChecked();
					}
				});
			}
		});
		recyclerView.getItemAnimator().setAddDuration(3000);
		recyclerView.getItemAnimator().setRemoveDuration(3000);
		recyclerView.getItemAnimator().setChangeDuration(3000);
		recyclerView.getItemAnimator().setChangeDuration(3000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.add_datasetchange) {
			int nextIndex = recyclerView.getAdapter().getItemCount();
			String newItem = generateDemoItem(nextIndex);
			((DemoAdapter)recyclerView.getAdapter()).insert(getCenterAdapterIndex(), newItem);
			recyclerView.getAdapter().notifyDataSetChanged();
			return true;
		}

		if (id == R.id.add_notifyitemadded) {
			int nextIndex = recyclerView.getAdapter().getItemCount();
			String newItem = generateDemoItem(nextIndex);
			int insertAdapterIndex = getCenterAdapterIndex();
			((DemoAdapter)recyclerView.getAdapter()).insert(insertAdapterIndex, newItem);
			recyclerView.getAdapter().notifyItemInserted(insertAdapterIndex);
			return true;
		}

		if (id == R.id.remove_datasetchange) {
			((DemoAdapter)recyclerView.getAdapter()).removeRange(getCenterAdapterIndex(), 3);
			recyclerView.getAdapter().notifyDataSetChanged();
			return true;
		}

		if (id == R.id.remove_notifyitemremoved) {
			int centerItemAdapterIndex = getCenterAdapterIndex();
			((DemoAdapter)recyclerView.getAdapter()).removeRange(centerItemAdapterIndex, 3);
			recyclerView.getAdapter().notifyItemRangeRemoved(centerItemAdapterIndex, 3);
		}



		return super.onOptionsItemSelected(item);
	}

	private List<String> generateDemoItems(int size) {
		List<String> result = new ArrayList<>(size);
		int index = 0;

		while (index < size) {
			result.add(generateDemoItem(index ++));
		}
		return result;
	}

	private String generateDemoItem(int index) {
		char ch = STRING_BASE.charAt(index % STRING_BASE.length());
		int len = random.nextInt(MAX_SIZE - MIN_SIZE) + MIN_SIZE;
		char[] chars = new char[len];
		Arrays.fill(chars, ch);
		return new String(chars);
	}

	private int getCenterAdapterIndex() {
		View firstView = recyclerView.getLayoutManager().getChildAt(0);
		int firstItemAdapterIndex = recyclerView.getChildAdapterPosition(firstView);
		View lastView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
		int lastItemAdapterIndex = recyclerView.getChildAdapterPosition(lastView);
		return firstItemAdapterIndex + (lastItemAdapterIndex - firstItemAdapterIndex) / 2;
	}
}
