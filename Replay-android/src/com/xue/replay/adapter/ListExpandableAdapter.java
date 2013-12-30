package com.xue.replay.adapter;

import java.util.List;

import com.xue.replay.R;
import com.xue.replay.entity.EventGroup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListExpandableAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<EventGroup> eventGroups;

	public ListExpandableAdapter(Context context, List<EventGroup> eventGroups) {
		this.context = context;
		this.eventGroups = eventGroups;
	}

	public void setEventGroup(List<EventGroup> eventGroups) {
		this.eventGroups = eventGroups;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return eventGroups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return eventGroups.get(groupPosition).getEventsSize();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return eventGroups.get(groupPosition).getGroupName();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return eventGroups.get(groupPosition).getEvent(childPosition).eventName;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.event_group_item, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.textView_group);
		textView.setText(getGroup(groupPosition).toString());
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.event_item, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.event);
		textView.setText(getChild(groupPosition, childPosition).toString());
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
