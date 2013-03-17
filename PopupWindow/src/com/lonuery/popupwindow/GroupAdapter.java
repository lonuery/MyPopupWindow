package com.lonuery.popupwindow;

import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GroupAdapter extends BaseAdapter{

	List<String> groupList;//群组信息列表
	Context context;
	private OnClickListener onClickListener;
	private OnItemClickListener mOnItemClickListener;
	private int selectedPos=-1;
	private String selectedText=null;
	
	public GroupAdapter(List<String> list,Context context){
		this.groupList = list;
		this.context=context;
		init();
	}
	
	@Override
	public int getCount() {		
		return groupList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {		
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView group_switch;
		TextView name;
		ImageView avatar;
		if(convertView == null){
			RelativeLayout ll = (RelativeLayout)LayoutInflater.from(context).inflate(R.layout.popupwindow, null);
			convertView=ll;
			avatar = (ImageView)ll.findViewById(R.id.groupAvatar);
			name = (TextView)ll.findViewById(R.id.groupName);
			group_switch =(TextView)ll.findViewById(R.id.groupSwitch);
		}else{
			avatar = (ImageView)convertView.findViewById(R.id.groupAvatar);
			name = (TextView)convertView.findViewById(R.id.groupName);
			group_switch =(TextView)convertView.findViewById(R.id.groupSwitch);
		}
		if(selectedText!=null){
			if(groupList.get(position).equals(selectedText)){
				name.setText(groupList.get(position).toString());
				group_switch.setText("当前组");
				avatar.setImageResource(R.drawable.online_avatar);
				
				name.setTextColor(context.getResources().getColor(R.color.memberList));
				group_switch.setTextColor(context.getResources().getColor(R.color.memberList));	
			}else{
				name.setText(groupList.get(position).toString());
				group_switch.setText("切换群组");
				avatar.setImageResource(R.drawable.default_avatar_shadow);
				
				name.setTextColor(context.getResources().getColor(R.color.normal));
				group_switch.setTextColor(context.getResources().getColor(R.color.normal));	
			}
		}else{
			if(groupList.get(position).equals("item4")){
				name.setText(groupList.get(position).toString());
				group_switch.setText("当前组");
				avatar.setImageResource(R.drawable.online_avatar);
				
				name.setTextColor(context.getResources().getColor(R.color.memberList));
				group_switch.setTextColor(context.getResources().getColor(R.color.memberList));	
			}else{
				name.setText(groupList.get(position).toString());
				group_switch.setText("切换群组");
				avatar.setImageResource(R.drawable.default_avatar_shadow);
				
				name.setTextColor(context.getResources().getColor(R.color.normal));
				group_switch.setTextColor(context.getResources().getColor(R.color.normal));	
			}
		}
		Log.i("name", groupList.get(position));
		convertView.setTag(position);			
		convertView.setOnClickListener(onClickListener);
		return convertView;
	}
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		mOnItemClickListener = listener;
	}

	/**
	 * 重新定义菜单选项单击接口
	 */
	public interface OnItemClickListener {
		public void onItemClick(View view, int position);
	}
	
	private void init() {
		onClickListener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				selectedPos = (Integer)view.getTag();
				Log.i("position:selectedPos", selectedPos+"");
				setSelectedPosition(selectedPos);
				if (mOnItemClickListener != null){
					mOnItemClickListener.onItemClick(view, selectedPos);
				}
			}
		};
	}
	
	/**
	 * 设置选中的position,并通知列表刷新
	 */
	public void setSelectedPosition(int pos){
		if (groupList != null && pos< groupList.size()){
			selectedPos = pos;
			selectedText = groupList.get(pos);
			notifyDataSetChanged();
		}
	}
}
