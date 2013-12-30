package com.xue.replay.entity;

import java.util.List;

public class EventGroup {

	private String groupName;
	private List<Event> events;

	public EventGroup(String groupName, List<Event> events) {
		this.groupName = groupName;
		this.events = events;
	}

	public void add(Event e) {
		events.add(e);
	}

	public void remove(Event e) {
		events.remove(e);
	}

	public Event getEvent(int index) {
		return events.get(index);
	}

	public String getGroupName() {
		return groupName;
	}

	public int getEventsSize() {
		return events.size();
	}

}
