//package com.jia.libui.sildeItemLayout;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//
//import com.jia.libui.R;
//
//
//public class SlideListAdapter extends BaseAdapter {
//
//	private Context mContext;
//	private LayoutInflater mInflater;
//
//	private SlideManager slideManager;
//
//	public SlideListAdapter(Context mContext) {
////		super();
//		this.mContext = mContext;
//		mInflater = LayoutInflater.from(mContext);
//		slideManager = new SlideManager();
//	}
//
//	public SlideManager getSlideManager() {
//		return slideManager;
//	}
//
//	@Override
//	public int getCount() {
//		return 50;
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return null;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		ViewHolder mHolder;
//		if (convertView == null) {
//			convertView = mInflater.inflate(R.layout.repair_record_list_item01, parent, false);
//			mHolder = new ViewHolder();
//			mHolder.mCancelCall = (Button) convertView
//					.findViewById(R.id.bt_call);
//			mHolder.mDeleteCell = (Button) convertView
//					.findViewById(R.id.bt_delete);
//			convertView.setTag(mHolder);
//		} else {
//			mHolder = (ViewHolder) convertView.getTag();
//		}
//		// 14、第一个问题处理：当滑动视图处于打开状态，点击条目之外其他的位置需要将原来条目关闭？
//		// 解决方案：需要在Adapter中给每一个Item绑定点击事件
//		SlidingItemLayout view = (SlidingItemLayout) convertView;
//		//默认关闭
//		view.closeSlidingLayout(false, false);
//
//		//给我们的滑动视图绑定回调监听（监听生命周期）
//		view.setOnSlideItemListener(slideManager.getOnSlideItemListener());
//		//一旦你点击了contentView我们立马将原来的已打开的视图关闭
//		view.getContentView().setOnClickListener(new View.OnClickListener(){//这里相当与添加了clickable=true 那么该View的onTouchEvent 会返回true
//			@Override
//			public void onClick(View v) {
//				slideManager.closeAllLayout();
//			}
//		});
//		return convertView;
//	}
//
//	class ViewHolder {
//		public Button mCancelCall;
//		public Button mDeleteCell;
//	}
//
//}