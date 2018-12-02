/*
 * Copyright (c) 2011 Marvin S. Mueller  All rights reserved.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.PriorityQueue;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import javax.swing.*;

/**
 * Class EventNotifier which stores several events and opens a notification window at a specified time 
 * @author Marvin S. Mueller
 *
 */
public class EventNotifier{
	protected JFrame alarmFrame;
	//protected Event events;
	protected PriorityQueue<Event> events;
	private JTextArea listedEvents;
	private JButton close;
	private JButton refresh;
	private JButton newEvent;
	private EventNotifier ae;

	/**
	 * 
	 */
	public EventNotifier() {
		super();
		ae =this;
		this.events = new PriorityQueue<Event>();
		this.alarmFrame = new JFrame("Event Timer");
		alarmFrame.setVisible(true);
		alarmFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		double space = 10;
		double size[][] = { // columns
				{ space, TableLayoutConstants.FILL,TableLayoutConstants.FILL, space },
				// Rows
						{ space, 30,space,TableLayoutConstants.FILL ,space,30,space} };
		alarmFrame.setLayout(new TableLayout(size));
		close = new JButton("Schliessen");
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);				
			}
		});
		
		refresh = new JButton("Aktuallisieren");
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshOutput();		
			}
		});
		newEvent = new JButton("Neuer Termin");
		newEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EventFrame(ae);
			}
		});
		listedEvents = new JTextArea("Bisher keine Termine eingetragen", 1, 1);
		listedEvents.setEditable(false);
		JScrollPane scr = new JScrollPane(listedEvents);
		alarmFrame.setSize(330,300);
		alarmFrame.add(close, "2,1");
		alarmFrame.add(newEvent, "1,1");
		alarmFrame.add(scr, "1,3,2,3");
		alarmFrame.add(refresh, "1,5,2,5");
		alarmFrame.validate();
	}
	
	/**
	 * adds the event to this
	 * @param event to add to the list
	 */
	public void addEvent(Event ev){
		this.events.add(ev);
		this.refreshOutput();
	}

	/**
	 * refreshes the output text area on the frame
	 */
	public void refreshOutput() {
		PriorityQueue<Event> newQueue = new PriorityQueue<Event>();
		Event currEvent;
		String eventText = "";
		this.listedEvents.setText(null); // clear textArea
		this.listedEvents.setRows(this.events.size());

		currEvent = this.events.poll();
		
		if (currEvent==null)
			eventText = "Aktuell keine Termine eingetragen.\n";
		
		while(currEvent != null){
			if (currEvent.getTime() >= System.currentTimeMillis()){
				newQueue.add(currEvent);
				eventText += "-->" + currEvent.toString() + "\n"; 
			}
			currEvent = this.events.poll();
		}
		int min = Calendar.getInstance().get(Calendar.MINUTE);
		eventText += "\n" + "Aktuelle Uhrzeit: " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + (min<10 ? 0 +""+ min : min) + " Uhr";
		this.listedEvents.setText(eventText);
		this.events = newQueue;
		this.alarmFrame.validate();
	}
}

