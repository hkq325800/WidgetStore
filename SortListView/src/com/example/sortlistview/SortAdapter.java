package com.example.sortlistview;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SortAdapter extends BaseAdapter implements SectionIndexer {
	private List<SortModel> list = null;
	private SortModel country;
	private Context mContext;
	private ViewHolder holder1, holder2;
	boolean isFilterMode = false;

	public SortAdapter(Context mContext, List<SortModel> list) {
		this.mContext = mContext;
		this.list = list;
	}

	public void isFilterMode(boolean isFilterMode) {
		this.isFilterMode = isFilterMode;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<SortModel> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		country = (SortModel) getItem(position);
		if (country.isGroup()) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		country = (SortModel) list.get(position);
		// final SortModel mContent = list.get(position);
		int type = getItemViewType(position);
		if (view == null) {
			switch (type) {
			case 0:
				holder1 = new ViewHolder();
				view = LayoutInflater.from(mContext).inflate(
						R.layout.item_group, parent, false);
				holder1.tvTitle = (TextView) view.findViewById(R.id.catalog);
				view.setTag(holder1);
				break;
			case 1:
				holder2 = new ViewHolder();
				view = LayoutInflater.from(mContext).inflate(R.layout.item,
						parent, false);
				holder2.lineBottom = (FrameLayout) view.findViewById(R.id.lineBottom);
				holder2.tvTitle = (TextView) view.findViewById(R.id.title);
				holder2.lineTop = (FrameLayout) view.findViewById(R.id.lineTop);
				view.setTag(holder2);
				break;
			}
		} else {
			switch (type) {
			case 0:
				holder1 = (ViewHolder) view.getTag();
				break;
			case 1:
				holder2 = (ViewHolder) view.getTag();
				break;
			}
		}

		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);

		// if (!thisLetter.equals("A") && arg2.getChildAt(position - 1) != null)
		// {
		// if (!thisLetter.equals(lastLetter)) {
		// ViewHolder v = (ViewHolder) arg2.getChildAt(position - 1)
		// .getTag();
		// LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
		// v.lineTop.getLayoutParams());
		// lp.setMargins(0, 0, 0, 0);
		// v.lineTop.setLayoutParams(lp);
		// } else {
		// LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
		// viewHolder.lineTop.getLayoutParams());
		// lp.setMargins(30, 0, 0, 0);
		// viewHolder.lineTop.setLayoutParams(lp);
		// }
		// }
		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现

		switch (type) {
		case 0:
			holder1.tvTitle.setText(country.getSortLetters());
			break;
		case 1:
			holder2.tvTitle.setText(country.getName());
			int num = getPositionForSection(section);
			if (isFilterMode) {
				holder2.lineTop.setVisibility(View.GONE);
				holder2.lineBottom.setVisibility(View.VISIBLE);
			} else {
				holder2.lineBottom.setVisibility(View.GONE);
				if (position == num + 1) {
					Log.d("num", num + "/" + position + "gone");
					holder2.lineTop.setVisibility(View.GONE);
				} else {
					Log.d("num", num + "/" + position + "VISIBLE");
					holder2.lineTop.setVisibility(View.VISIBLE);
				}
			}
			break;
		}
		return view;

	}

	final static class ViewHolder {
		TextView tvTitle;
		FrameLayout lineTop, lineBottom;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	// private String getAlpha(String str) {
	// String sortStr = str.trim().substring(0, 1).toUpperCase();
	// // 正则表达式，判断首字母是否是英文字母
	// if (sortStr.matches("[A-Z]")) {
	// return sortStr;
	// } else {
	// return "#";
	// }
	// }

	@Override
	public Object[] getSections() {
		return null;
	}
}