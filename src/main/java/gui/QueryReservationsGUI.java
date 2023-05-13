package gui;

import businessLogic.BLFacade;

import configuration.UtilDate;

import com.toedter.calendar.JCalendar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;


public class QueryReservationsGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	
	private String email;
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane ScrollPaneSessions = new JScrollPane();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;

	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Hall"),
			ResourceBundle.getBundle("Etiquetas").getString("Data"),
			ResourceBundle.getBundle("Etiquetas").getString("Starts"),
			ResourceBundle.getBundle("Etiquetas").getString("Ends"),
			ResourceBundle.getBundle("Etiquetas").getString("ActivityName"),
			ResourceBundle.getBundle("Etiquetas").getString("ActivityLevel"),
	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private final JLabel ErrorMessage = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$

	public QueryReservationsGUI(String userEmail)
	{
		this.email = userEmail;
		try
		{
			jbInit(userEmail);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void jbInit(String email) throws Exception
	{

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(40, 422, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);


		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		BLFacade facade = MainGUI.getBusinessLogic();

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					 
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						
						
						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();
					}
													
					

					try {
						
						BLFacade facade=MainGUI.getBusinessLogic();


					} catch (Exception e1) {
						
					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		ScrollPaneSessions.setBounds(new Rectangle(292, 50, 346, 150));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		ScrollPaneSessions.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		this.getContentPane().add(ScrollPaneSessions, null);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryActivitiesGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				ErrorMessage.setText("");
				tableModelEvents.setDataVector(null, columnNamesEvents);
				
				Date data = (Date) jCalendar1.getDate();
				data.setMinutes(0);
				data.setSeconds(0);
				
				// get sessiosn
				Vector<domain.Erreserba> reservations = facade.logReservationsByDate(data);
				if(reservations.size() == 0) ErrorMessage.setText("There is no reservations for this day");
				for (domain.Erreserba reserv : reservations){
					Vector<Object> row = new Vector<Object>();

					System.out.println("Events " + e.toString());

					String mode;
					if(reserv.getMode()) mode = "accepted";
					else mode = "waiting";
					
					row.add(reserv.getSaioa().getAretoa().toString());
					row.add(reserv.getDate().getDate());
					row.add(reserv.getSaioa().getHasierakoData().getHours());
					row.add(reserv.getSaioa().getAmaierakoData().getHours());
					row.add(reserv.getSaioa().getSaiokoJarduera().toString());
					row.add(mode); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
					tableModelEvents.addRow(row);		
				}
				tableEvents.getColumnModel().getColumn(0).setPreferredWidth(75);
				tableEvents.getColumnModel().getColumn(1).setPreferredWidth(70);
				tableEvents.getColumnModel().getColumn(2).setPreferredWidth(25);
				tableEvents.getColumnModel().getColumn(3).setPreferredWidth(25);
				tableEvents.getColumnModel().getColumn(4).setPreferredWidth(75);
				tableEvents.getColumnModel().getColumn(5).setPreferredWidth(70);
				
			}
		});
		btnNewButton.setBounds(180, 422, 130, 30);
		getContentPane().add(btnNewButton);
		ErrorMessage.setForeground(Color.RED);
		ErrorMessage.setBounds(40, 315, 591, 39);
		
		getContentPane().add(ErrorMessage);

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		UserGUI u = new UserGUI(this.email);
		u.setVisible(true);
	}
}