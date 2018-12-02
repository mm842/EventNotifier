/*
 * Copyright (c) 2011 Marvin S. Mueller  All rights reserved.
 */

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Thread running for each created event to start the alarm
 * @author Marvin S. Mueller
 *
 */
public class TimerThread extends Thread {
	private EventNotifier ac;
	private Event ev;
	private JFrame rem;
	/**
	 * the duration of breaks
	 */
	protected int precision = 1000;

	public TimerThread() {
		super();
	}

	/**
	 * @param tCore
	 */
	public TimerThread(EventNotifier ac) {
		super();
		this.ac = ac;
	}
	
	/**
	 * Creates a new TimerThread with an given event to remember
	 * @param ev event to remember
	 */
	public TimerThread(Event ev){
		super();
		this.ev = ev;
	}

	/**
	 * increases the time with precision saved in object
	 */
	@Override
	public void run() {
		while (!isInterrupted()) {
			long time = ev.getTime();
			time -= System.currentTimeMillis();
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			rem = new JFrame(ev.getName());
			JButton close = new JButton("Schlieﬂen");
			close.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					rem.dispose();
					//ac.alarmFrame.dispose();
					//System.exit(0);	
				}
			});
			
			double size[][] = {	{10, TableLayoutConstants.PREFERRED,10},
					{10,TableLayoutConstants.PREFERRED,10, 30 ,10}	};
			rem.setSize(200,300);
			rem.setLayout(new TableLayout(size));
			rem.add(close, "1,3");
			JLabel desc = new JLabel(ev.getDesc());
			rem.add(desc, "1,1");
			rem.setAlwaysOnTop(true);
			rem.setVisible(true);
			ev.ac.refreshOutput();
			/* get sure the thread terminates */
			return;
		}
	}
	
}
