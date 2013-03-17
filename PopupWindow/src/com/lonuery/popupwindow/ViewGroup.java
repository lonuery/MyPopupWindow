package com.lonuery.popupwindow;

import java.util.ArrayList;
import java.util.List;
import com.lonuery.popupwindow.GroupAdapter.OnItemClickListener;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;


public class ViewGroup extends RelativeLayout{
	
	private List<String> groupList = new ArrayList<String>();//œ‘ æ◊÷∂Œ
	private GroupAdapter adapter;
	private String showText="≤‚ ‘003";
	private OnSelectListener mOnSelectListener;
	private ListView listView;
	Context context;
	public ViewGroup(Context context,List<String>list) {
		super(context);
		this.groupList = list;
		this.context = context;
		init();
	}
		
	public String getShowText() {
		return showText;
	}
	
	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener{
		public void getValue(String distance, String showText);
	}
	
	private void init(){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_distance, this, true);
		setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_mid));
		listView = (ListView) findViewById(R.id.listView);
		adapter = new GroupAdapter(groupList, context);
		listView.setAdapter(adapter);
		
		adapter.setOnItemClickListener(itemClickListener());
	}
	
	private GroupAdapter.OnItemClickListener itemClickListener(){
		GroupAdapter.OnItemClickListener go = new OnItemClickListener() {			
			@Override
			public void onItemClick(View view, int position) {
				if (mOnSelectListener != null){
					showText = groupList.get(position);
					mOnSelectListener.getValue(String.valueOf(position+1), groupList.get(position));
				}
			}
		};
		
		return go;
	}
}
