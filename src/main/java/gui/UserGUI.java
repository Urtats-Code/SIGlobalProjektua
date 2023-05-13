package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class UserGUI extends JFrame{
	public UserGUI(String email) {
		setTitle("User");
		setSize(409, 291);
		getContentPane().setLayout(null);
		
		JLabel Label = new JLabel("Email: ");
		Label.setBounds(10, 11, 55, 14);
		Label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		getContentPane().add(Label);
		
		JLabel UserEmail = new JLabel(email);
		UserEmail.setBounds(66, 11, 291, 14);
		UserEmail.setFont(new Font("SansSerif", Font.PLAIN, 18));
		getContentPane().add(UserEmail);
		
		JButton GoToQueryActivities = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryActivities"));
		GoToQueryActivities.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QueryActivitiesGUI gui= new QueryActivitiesGUI(2, email);
				gui.setVisible(true);
				UserGUI.this.setVisible(false);
			}
		});
		GoToQueryActivities.setBounds(54, 48, 303, 21);
		getContentPane().add(GoToQueryActivities);
		
		JButton GoToQueryReservations = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryReservations"));
		GoToQueryReservations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QueryReservationsGUI gui= new QueryReservationsGUI(email);
				gui.setVisible(true);
				UserGUI.this.setVisible(false);
			}
		});
		GoToQueryReservations.setBounds(54, 79, 303, 21);
		getContentPane().add(GoToQueryReservations);
		
		JButton GoToMakeReservations = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MakeReservations"));
		GoToMakeReservations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MakeReservationsGUI gui= new MakeReservationsGUI(email);
				gui.setVisible(true);
				UserGUI.this.setVisible(false);
			}
		});
		GoToMakeReservations.setBounds(54, 110, 303, 21);
		getContentPane().add(GoToMakeReservations);
		
		JButton GoToCancelReservations = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CancelReservations"));
		GoToCancelReservations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CancelReservationsGUI gui= new CancelReservationsGUI(email);
				gui.setVisible(true);
				UserGUI.this.setVisible(false);
			}
		});
		GoToCancelReservations.setBounds(54, 141, 303, 21);
		getContentPane().add(GoToCancelReservations);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI gui= new MainGUI();
				gui.setVisible(true);
				UserGUI.this.setVisible(false);
			}
		});
		btnNewButton.setBounds(104, 181, 206, 21);
		getContentPane().add(btnNewButton);
	}
}
