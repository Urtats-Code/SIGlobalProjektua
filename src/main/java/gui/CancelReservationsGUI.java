package gui;

import java.text.DateFormat;
import java.util.*;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Areto;
import domain.Jarduera;
import domain.Saioa;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class CancelReservationsGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private String email;
	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
	

	 public CancelReservationsGUI(String userEmail) {
		this.email = userEmail;
		 try {
			jbInit(userEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(String email) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));

		jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonClose.setBounds(new Rectangle(40, 292, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jLabelListOfEvents, null);

		this.getContentPane().add(jCalendar, null);
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		
		Vector<domain.Jarduera> Activities = facade.logActivities();
		DefaultComboBoxModel<domain.Jarduera> modelActivities = new DefaultComboBoxModel<domain.Jarduera>();
		for(Jarduera a : Activities) {
			modelActivities.addElement(a);
		}
		
		JLabel ErrrorMessage = new JLabel(""); 
		ErrrorMessage.setForeground(Color.RED);
		ErrrorMessage.setFont(new Font("Tahoma", Font.PLAIN, 24));
		ErrrorMessage.setBounds(40, 233, 485, 39);
		getContentPane().add(ErrrorMessage);
		
		
		JComboBox<domain.Erreserba> ReservaLista = new JComboBox<domain.Erreserba>();
		ReservaLista.setBounds(new Rectangle(290, 47, 235, 20));
		ReservaLista.setBounds(290, 49, 235, 20);
		getContentPane().add(ReservaLista);
		
		// get the hall list
		Vector<domain.Areto> HallInfoList = facade.logAretoak();
		DefaultComboBoxModel<domain.Areto> modelHalls = new DefaultComboBoxModel<domain.Areto>();
		// add the list to the model
		for(domain.Areto hall : HallInfoList) {
			modelHalls.addElement(hall);
		}


		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		JButton SearchReservations = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		SearchReservations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset the value 
				ErrrorMessage.setText("");
				
				// Get reservations
				boolean response = facade.removeReservation((domain.Erreserba) ReservaLista.getSelectedItem(), email);
				if(!response) ErrrorMessage.setText("Something went wrong");
				
			}
		});
		SearchReservations.setBounds(180, 292, 130, 27);
		getContentPane().add(SearchReservations);
		
	

		
		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+calendarAnt.getTime());
					System.out.println("calendarAct: "+calendarAct.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolverá 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);
						
						BLFacade facade = MainGUI.getBusinessLogic();

						
					}



					//	Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					Date firstDay = UtilDate.trim(calendarAct.getTime());

					Date data = (Date) jCalendar.getDate();
					data.setMinutes(0);
					data.setSeconds(0);
					
					Vector<domain.Erreserba> reservations = facade.logReservationsByDate(data);
					DefaultComboBoxModel<domain.Erreserba> modelReservations = new DefaultComboBoxModel<domain.Erreserba>();
					for(domain.Erreserba reserv: reservations) {
						modelReservations.addElement(reserv);
					}
					
					ReservaLista.setModel(modelReservations);
					
				}
			}
		});
	}

	
public static void paintDaysWithEvents(JCalendar jCalendar,Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed to cyan.

		
		Calendar calendar = jCalendar.getCalendar();
		
		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;
		
		
	 	for (Date d:datesWithEventsCurrentMonth){

	 		calendar.setTime(d);
	 		System.out.println(d);
	 		

			
			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
	 	}
	 	
 			calendar.set(Calendar.DAY_OF_MONTH, today);
	 		calendar.set(Calendar.MONTH, month);
	 		calendar.set(Calendar.YEAR, year);

	 	
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		UserGUI a = new UserGUI(email);
		a.setVisible(true);
	}
}