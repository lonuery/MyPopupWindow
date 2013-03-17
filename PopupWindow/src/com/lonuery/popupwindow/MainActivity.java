package com.lonuery.popupwindow;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewGroup viewGroup;
	private ViewGroup viewGroup2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		List<String> list = new ArrayList<String>();
		list.add("item1");
		list.add("item2");
		list.add("item3");
		list.add("item4");
		list.add("item5");
		list.add("item6");
		list.add("item7");
		
		expandTabView = (ExpandTabView) findViewById(R.id.expandTabView1);
		viewGroup = new ViewGroup(this,list);
		viewGroup2 = new ViewGroup(this, list);
		
		mViewArray.add(viewGroup);
		mViewArray.add(viewGroup2);
		
		expandTabView.setValue(mViewArray);
		expandTabView.setTitle(viewGroup.getShowText(), 0);//设置Tile的显示位置
		expandTabView.setTitle(viewGroup2.getShowText(), 1);//设置Tile的显示位置
		
		viewGroup.setOnSelectListener(createOnSelectListenerOO());
		viewGroup2.setOnSelectListener(createOnSelectListenerOO());
	}
	
	private ViewGroup.OnSelectListener createOnSelectListenerOO(){
		ViewGroup.OnSelectListener listener = new ViewGroup.OnSelectListener() {			
			@Override
			public void getValue(String distance, String showText) {
				onRefresh(viewGroup, showText);
			}
		};
		
		return listener;
	}
	private void onRefresh(View view, String showText){		
		expandTabView.onPressBack();
		int position = getPositon(view);
				
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {//
			expandTabView.setTitle(showText, position);
		}
		Toast.makeText(MainActivity.this, showText, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 获取根据View对象获取其在列表中的位置*/
	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public void onBackPressed() {
		if (!expandTabView.onPressBack()) {
			finish();
		}		
	}
}
