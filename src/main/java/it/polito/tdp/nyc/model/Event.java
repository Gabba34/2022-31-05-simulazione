package it.polito.tdp.nyc.model;

public class Event implements Comparable<Event>{

	public enum EventType{
		START_HS,
		END_HS,
		NEW_BORO,
	}
	
	int time;
	EventType type;
	int tech;
	
	public Event(int time, EventType type, int tech) {
		super();
		this.time = time;
		this.type = type;
		this.tech = tech;
	}

	
	public int getTime() {
		return time;
	}


	public void setTime(int time) {
		this.time = time;
	}


	public EventType getType() {
		return type;
	}


	public void setType(EventType type) {
		this.type = type;
	}


	public int getTech() {
		return tech;
	}


	public void setTech(int tech) {
		this.tech = tech;
	}


	@Override
	public int compareTo(Event o) {
		return this.time-o.time;
	}

}
