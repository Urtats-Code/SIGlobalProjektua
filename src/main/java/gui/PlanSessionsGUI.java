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

public class PlanSessionsGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;
	private String email;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	

	 public PlanSessionsGUI(String email) {
		try {
			this.email=email;
			jbInit(email);
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
		
		

		JLabel Aretoak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Hall")); //$NON-NLS-1$ //$NON-NLS-2$
		Aretoak.setBounds(new Rectangle(290, 18, 277, 20));
		Aretoak.setBounds(290, 89, 277, 20);
		getContentPane().add(Aretoak);
		
		
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
		
		
		JComboBox<domain.Jarduera> JarduerakLista = new JComboBox<domain.Jarduera>();
		JarduerakLista.setBounds(new Rectangle(290, 47, 235, 20));
		JarduerakLista.setBounds(290, 49, 235, 20);
		getContentPane().add(JarduerakLista);
		JarduerakLista.setModel(modelActivities);
		
		// get the hall list
		Vector<domain.Areto> HallInfoList = facade.logAretoak();
		DefaultComboBoxModel<domain.Areto> modelHalls = new DefaultComboBoxModel<domain.Areto>();
		// add the list to the model
		for(domain.Areto hall : HallInfoList) {
			modelHalls.addElement(hall);
		}
		
		// set the model to the hall 
		JComboBox<domain.Areto> HallList = new JComboBox<domain.Areto>();
		HallList.setModel(modelHalls);
		
		
		HallList.setBounds(new Rectangle(290, 47, 235, 20));
		HallList.setBounds(290, 118, 235, 20);
		getContentPane().add(HallList);
		
		JSpinner StartSpinner = new JSpinner();
		StartSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		StartSpinner.setBounds(290, 170, 47, 30);
		getContentPane().add(StartSpinner);
		
		JSpinner EndSpinner = new JSpinner();
		EndSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		EndSpinner.setBounds(401, 170, 47, 30);
		getContentPane().add(EndSpinner);
		
		JLabel StartHour = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		StartHour.setFont(new Font("Tahoma", Font.PLAIN, 14));
		StartHour.setBounds(288, 145, 49, 14);
		getContentPane().add(StartHour);
		
		JLabel FinishHour = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PlanSessionsGUI.lblFinish.text")); //$NON-NLS-1$ //$NON-NLS-2$
		FinishHour.setFont(new Font("Tahoma", Font.PLAIN, 14));
		FinishHour.setBounds(401, 145, 49, 14);
		getContentPane().add(FinishHour);


		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		JButton CreteSaioa = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		CreteSaioa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset the value 
				ErrrorMessage.setText("");
				
				// get starting and finishing hour
				
				Object value = StartSpinner.getValue();
				Integer startingHour = Integer.parseInt(value.toString());
				
				Object value2 = EndSpinner.getValue();
				Integer finishingHour = Integer.parseInt(value2.toString());
				
				// Get hall
				
				Areto hall = (Areto) HallList.getSelectedItem();
				
				
				// Get activity
				
				Jarduera activity = (Jarduera) JarduerakLista.getSelectedItem();
				
				// Get date
				
				Date data = (Date) jCalendar.getDate();
				data.setHours(startingHour);
				data.setMinutes(0);
				data.setSeconds(0);
								
				
				Date dataEnd = (Date) jCalendar.getDate().clone();
				dataEnd.setHours(finishingHour);
				dataEnd.setMinutes(0);
				dataEnd.setSeconds(0);
				
				System.out.println("End hour: " + dataEnd.getHours());
				
				Saioa saioBerria = new Saioa(data, dataEnd, activity, hall);
				
				boolean response = false;
				
				// if the starting hour is smaller than the ending hour we don't show an error message
				if(startingHour < finishingHour) {
						response = facade.addSaioa(saioBerria);
				}
				
				
				if(!response) {
					ErrrorMessage.setText("Something went wrong");
				}
				
			}
		});
		CreteSaioa.setBounds(180, 292, 130, 27);
		getContentPane().add(CreteSaioa);
		
	

		
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

					try {
						BLFacade facade = MainGUI.getBusinessLogic();

						
						
					} catch (Exception e1) {

					}

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
		AdminGUI a= new AdminGUI(email);
		a.setVisible(true);
	}
}