package com.xue.replay.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ExpandableListView;

import com.xue.replay.adapter.ListExpandableAdapter;
import com.xue.replay.entity.Event;
import com.xue.replay.entity.EventGroup;

public class EventSourceHandle {

	private Context context;
	private FileHandle mFileHandle;
	private ExpandableListView expandableListView;
	private ListExpandableAdapter mListExpandableAdapter;
	
	private List<EventGroup> mEventGroups = new ArrayList<EventGroup>();

	public EventSourceHandle(Context context, ExpandableListView expandableListView) {
		this.context = context;
		this.expandableListView = expandableListView;
	}

	public void load() {
		mListExpandableAdapter = new ListExpandableAdapter(context, mEventGroups);
		mFileHandle = new FileHandle();
		String[] groupList = mFileHandle.getGroupList();
		if (groupList != null) {
			for (String groupName : groupList) {

				mEventGroups.add(getEvents(groupName));
			}
			expandableListView.setAdapter(mListExpandableAdapter);
		}
	}

	public EventGroup getEvents(String groupName) {
		String[] eventList = mFileHandle.getEventList(groupName);
		List<Event> mEvents = new ArrayList<Event>();
		for (String eventName : eventList) {
			mEvents.add(new Event(eventName, mFileHandle.getPath() + File.separator + groupName));
		}
		return new EventGroup(groupName, mEvents);
	}

	public boolean delete(int index) {
		boolean result = mFileHandle.deleteDir(mListExpandableAdapter.getGroup(index).toString());
		if (result == false)
			return false;
		mEventGroups.remove(index);
		mListExpandableAdapter.notifyDataSetChanged();
		return result;
	}

	public boolean add(String groupName) {
		boolean result = mFileHandle.createDir(groupName);
		if (result == false)
			return false;
		mEventGroups.add(getEvents(groupName));
		mListExpandableAdapter.notifyDataSetChanged();
		return result;
	}

	public ListExpandableAdapter getAdapter() {
		return mListExpandableAdapter;
	}

}
