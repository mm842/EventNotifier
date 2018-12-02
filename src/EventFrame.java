/*
 * Copyright 2011 Marvin S. Mueller All rights reserved.
 */


import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.*;

/**
 * 
 */

/**
 * Graphical user interface
 * @author Marvin S. Mueller
 * |
 */
public class EventFrame extends JFrame {
	private EventFrame eFrame;
	private JButton ok;
	private JButton cancel;
	private JLabel nameL;
	private JLabel descL;
	private JLabel timeL;
	private JTextField nameF;
	private JTextArea descF;
	private JTextField timeF;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7719173490121141179L;

	public EventFrame(final EventNotifier ac){
		super("Neuen Termin eintragen");
		double space = 10;
		double size[][] = { // columns
				{ space, 150, 3*space,150, space },
				// Rows
						{ space,30,space,30,space,30,space, 80,2*space,30,space} };
		this.setLayout(new TableLayout(size));
		nameL = new JLabel("Eventname:");
		descL = new JLabel("Beschreibung:");
		timeL = new JLabel("Weckzeit [hh:mm]");
		
		nameF = new JTextField();
		descF = new JTextArea();
		timeF = new JTextField();
		
		this.add(nameL, "1,1");
		this.add(nameF, "3,1");
		this.add(timeL, "1,3");
		this.add(timeF, "3,3");
		this.add(descL, "1,5");
		this.add(descF, "1,7,3,7");
		
		ok = new JButton("OK");
		cancel = new JButton("Abbrechen");
		eFrame = this;
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String tStr = timeF.getText();
				int k = tStr.lastIndexOf(":");
				int h = Integer.parseInt(tStr.substring(0, k));
				int min = Integer.parseInt(tStr.substring(k+1));
				//Calendar now = Calendar.getInstance();
				Calendar then = Calendar.getInstance();
				then.set(Calendar.HOUR_OF_DAY, h);
				then.set(Calendar.MINUTE, min);
				long time = then.getTimeInMillis();
				if(time< System.currentTimeMillis() ){
					then.set(Calendar.DAY_OF_MONTH, then.get(Calendar.DAY_OF_MONTH)+1);
					time = then.getTimeInMillis();
				}
				if(time<0){
					then.set(Calendar.MONTH,then.get(Calendar.MONTH)+1);
					then.set(Calendar.DAY_OF_MONTH, 1);
					time = then.getTimeInMillis();
				}
				
				//System.out.println(time);
				Event ev = new Event(nameF.getText(), descF.getText(),time,ac);
				ac.events.add(ev);
				ev.activate();
				eFrame.dispose();
			}
		});
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eFrame.dispose();		
			}
		});
		this.add(ok, "1,9");
		this.add(cancel, "3,9");
		this.setSize(370,350);
		this.setVisible(true);
	}
	
}
