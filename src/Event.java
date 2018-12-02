/*
 * Copyright (c) 2011 Marvin S. Mueller  All rights reserved.
 */


import java.util.Calendar;



/**
 * A date-entry of the alarm-clock
 * @author Marvin
 *
 */
public class Event implements Comparable<Event>{
	/** 
	 * the name 
	 */
	private String name;
	/** 
	 * the describtion 
	 */
	private String desc;
	/** 
	 * the time 
	 * format: [h,m] 
	 */
	private long time;
	protected EventNotifier ac;
	private TimerThread tT;
	private boolean isActive;
	
	/**
	 * Constructor for an Event
	 * @param name
	 * @param desc
	 * @param time
	 */
	public Event(String name, String desc, long time) {
		super();
		this.name = name;
		this.desc = desc;
		this.time = time;
		this.isActive = false;
		this.tT = new TimerThread(this);
	}
	
	/**
	 * Constructor for an Event which belongs to the EventNotifier ac
	 * @param name
	 * @param desc
	 * @param time
	 */
	public Event(String name, String desc, long time, EventNotifier ac) {
		super();
		this.name = name;
		this.desc = desc;
		this.time = time;
		this.isActive = false;
		this.ac = ac;
		this.tT = new TimerThread(this);
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(time);
		int min = date.get(Calendar.MINUTE);
		return "Termin:" + name + ", Weckzeit: "
				+ date.get(Calendar.HOUR_OF_DAY) + ":" + (min < 10 ? 0 +""+ min : min ) + " Uhr"+ "\n" + "Beschreibung: " + desc;
	}
	
	/*
	 *  compares this with event o by time remaining.
	 *  @param o Event to compare with
	 *  @return negative if this happens before o, positive else
	 */
	@Override
	public int compareTo(Event o) {
		if( o.time == this.time){
			return 0;
		}
		return (this.time <  o.time ? -1 : 1 )  ;
	}
	
	/**
	 * Starting the thread.
	 */
	public void activate() {
		if(!this.isActive){
			this.isActive = true;
			this.tT.start();
			this.ac.refreshOutput();
		}
		
	}
}
