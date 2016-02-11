package com.rhapsody.xhan.recyclerviewdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.rhapsody.xhan.recyclerviewdemo.view.DemoAdapter;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.lang.reflect.Array;
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
	Spinner layoutSpinner;

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
				CheckBox supportPredictiveAnimationCheck = (CheckBox)findViewById(R.id.support_predictive_animation);
				recyclerView.setLayoutManager(createLayoutManager(supportPredictiveAnimationCheck.isChecked()));
			}
		});
		recyclerView.getItemAnimator().setAddDuration(3000);
		recyclerView.getItemAnimator().setRemoveDuration(3000);
		recyclerView.getItemAnimator().setChangeDuration(3000);
		recyclerView.getItemAnimator().setChangeDuration(3000);

		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.layouts, android.R.layout.simple_spinner_dropdown_item);
		layoutSpinner = (Spinner)findViewById(R.id.layouts);
		layoutSpinner.setAdapter(spinnerAdapter);
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

	private RecyclerView.LayoutManager createLayoutManager(final boolean supportPredictiveAnimation) {
		String layoutName = layoutSpinner.getSelectedItem().toString();
		RecyclerView.LayoutManager layoutManager = null;
		if (layoutName.equals("Linear")) {
			layoutManager = new LinearLayoutManager(this) {
				@Override
				public boolean supportsPredictiveItemAnimations() {
					return supportPredictiveAnimation;
				}
			};
		} else if (layoutName.equals("Grid")) {
			layoutManager = new GridLayoutManager(this, 3) {
				@Override
				public boolean supportsPredictiveItemAnimations() {
					return supportPredictiveAnimation;
				}
			};
		} else if (layoutName.equals("Stagger")) {
			layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL) {
				@Override
				public boolean supportsPredictiveItemAnimations() {
					return supportPredictiveAnimation;
				}
			};
		} else if (layoutName.equals("Flow")) {
			layoutManager = new FlowLayoutManager() {
				@Override
				public boolean supportsPredictiveItemAnimations() {
					return supportPredictiveAnimation;
				}
			};
		}
		return layoutManager;
	}
}
