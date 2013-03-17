package com.lonuery.popupwindow;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * �˵��ؼ�ͷ������װ��������������̬����ͷ����ť����
 * 
 * @author lonuery
 */

public class ExpandTabView extends LinearLayout implements OnDismissListener {

	private ToggleButton selectedButton;
	private ArrayList<RelativeLayout> mViewArray = new ArrayList<RelativeLayout>();
	private ArrayList<ToggleButton> mToggleButton = new ArrayList<ToggleButton>();// ����ÿ��Tab�ϵ�ToggleButton
	private Context mContext;
	private final int SMALL = 0;
	private int displayWidth;//�ֻ���Ļ�ֱ��ʵĿ��
	private int displayHeight;//�ֻ���Ļ�ֱ��ʵĳ���
	private PopupWindow popupWindow;
	private int selectPosition;

	public ExpandTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * ����ѡ���λ������tabitem��ʾ��ֵ
	 */
	public void setTitle(String valueText, int position) {
		if (position < mToggleButton.size()) {
			mToggleButton.get(position).setText(valueText);
		}
	}

	/**
	 * ����ѡ���λ�û�ȡtabitem��ʾ��ֵ
	 */
	public String getTitle(int position) {
		if (position < mToggleButton.size() && mToggleButton.get(position).getText() != null) {
			return mToggleButton.get(position).getText().toString();
		}
		return "";
	}

	/**
	 * ����tabitem�ĸ����ͳ�ʼֵ
	 */
	public void setValue(ArrayList<View> viewArray) {
		if (mContext == null) {
			return;
		}
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		for (int i = 0; i < viewArray.size(); i++) {
			final RelativeLayout r = new RelativeLayout(mContext);
			int maxHeight = (int) (displayHeight * 0.7);
			RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 
					maxHeight);//���ͳ�һ��layout������������߶�
			rl.leftMargin = 10;//�������ͳ�����View������߽�ľ���
			rl.rightMargin = 10;
			r.addView(viewArray.get(i), rl);//���ض���View���ý����ͳ�����layout r1��
			mViewArray.add(r);
			r.setTag(SMALL);
			
			// ��ȡÿ��Tab�ϵ�ToggleButton���󣬲�����
			ToggleButton tButton = (ToggleButton) inflater.inflate(R.layout.toggle_button, this, false);
			addView(tButton);
			
			addSeperateLine(i,viewArray);//����ָ���
			
			mToggleButton.add(tButton);//����ÿ��Layout��ToggleButton�Ķ����Ա��Ժ���������ʾ������
			tButton.setTag(i);//Ϊÿ��Tab �ϵ� ToggleButton ���ñ�־
			
			//��������ط� PopupWindownҲ��ʧ
			r.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onPressBack();
				}
			});
			//����ÿ��PopupWindow�ı�����ɫ
			r.setBackgroundColor(mContext.getResources().getColor(R.color.popup_main_background));
			
			//����ÿ��ToggleButton��״̬
			tButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					// initPopupWindow();
					ToggleButton tButton = (ToggleButton) view;

					if (selectedButton != null && selectedButton != tButton) {
						selectedButton.setChecked(false);//�ָ�ǰһ��button�ĵ��״̬Ϊδ���״̬
					}
					selectedButton = tButton;
					selectPosition = (Integer) selectedButton.getTag();
					startAnimation();
//					if (mOnButtonClickListener != null && tButton.isChecked()) {
//						mOnButtonClickListener.onClick(selectPosition);
//					}
				}
			});
		}
	}
	
	private void startAnimation() {
		if (popupWindow == null) {
			popupWindow = new PopupWindow(mViewArray.get(selectPosition), displayWidth, displayHeight);
			popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
			popupWindow.setFocusable(false);
			popupWindow.setOutsideTouchable(true);
		}	
		if (selectedButton.isChecked()) {
			if (!popupWindow.isShowing()){
				showPopup(selectPosition);
			} else {
				popupWindow.setOnDismissListener(this);
				popupWindow.dismiss();
				hideView();
			}
		} else {
			if (popupWindow.isShowing()) {
				popupWindow.dismiss();
				hideView();
			}
		}
	}

	private void showPopup(int position) {
		View tView = mViewArray.get(selectPosition).getChildAt(0);
		if (tView instanceof ViewBaseAction) {
			ViewBaseAction f = (ViewBaseAction) tView;
			f.show();
		}
		//�������ͬһ��Tab������һ��Tab��child����ΪpopupWindow������
		if (popupWindow.getContentView() != mViewArray.get(position)) {
			popupWindow.setContentView(mViewArray.get(position));
		}
		popupWindow.showAsDropDown(this, 0, 0);//��ʾpopupWindow
	}

	/**
	 * ����˵���չ��״̬�����ò˵��ջ�ȥ
	 */
	public boolean onPressBack() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			hideView();
			if (selectedButton != null) {
				selectedButton.setChecked(false);
			}
			return true;
		} else {
			return false;
		}

	}

	private void hideView() {
		View tView = mViewArray.get(selectPosition).getChildAt(0);
		if (tView instanceof ViewBaseAction) {
			ViewBaseAction f = (ViewBaseAction) tView;
			f.hide();
		}
	}

	private void init(Context context) {
		mContext = context;
		displayWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
		displayHeight = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
		setOrientation(LinearLayout.HORIZONTAL);//���������ó��е���ʾ��ʽ
	}

	@Override
	public void onDismiss() {
		showPopup(selectPosition);
		popupWindow.setOnDismissListener(null);
	}

	/*private OnButtonClickListener mOnButtonClickListener;

	*//**
	 * ����tabitem�ĵ�������¼�
	 *//*
	public void setOnButtonClickListener(OnButtonClickListener l) {
		mOnButtonClickListener = l;
	}

	*//**
	 * �Զ���tabitem����ص��ӿ�
	 *//*
	public interface OnButtonClickListener {
		public void onClick(int selectPosition);
	}*/

	/**
	 * ��ÿ��Tab֮���һ���ָ���
	 * */
	public void addSeperateLine(int i,ArrayList<View> viewArray){
		View line = new TextView(mContext);
		line.setBackgroundResource(R.drawable.choosebar_line);			
		//�����n��Tab ��ֻ��Ҫn-1���ָ��ߣ�ÿ����һ��Tab���һ���ָ��ߣ����һ������Ҫ��
		if (i < viewArray.size() - 1) {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(2, LinearLayout.LayoutParams.FILL_PARENT);
			addView(line, lp);//���ָ����߷���ָ����layout��Ӣ��API���ͣ�Adds a child view with the specified layout parameters. 
		}
	}
}
