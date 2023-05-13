package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;

public class AdminGUI extends JFrame{
	public AdminGUI(String email) {
		setTitle("User");
		setSize(371, 300);
		
		getContentPane().setLayout(null);
		
		JLabel Label = new JLabel("Email: ");
		Label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		Label.setBounds(10, 11, 55, 14);
		getContentPane().add(Label);
		
		JLabel UserEmail = new JLabel(email);
		UserEmail.setFont(new Font("SansSerif", Font.PLAIN, 18));
		UserEmail.setBounds(66, 6, 250, 24);
		getContentPane().add(UserEmail);
		
		JButton GoToCreateActivities = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateActivities"));
		GoToCreateActivities.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateActivitiesGUI gui= new CreateActivitiesGUI(email);
				gui.setVisible(true);
				AdminGUI.this.setVisible(false);
			}
		});
		GoToCreateActivities.setBounds(66, 84, 224, 50);
		getContentPane().add(GoToCreateActivities);
		
		JButton GoToPlanSessions = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PlanSessions"));
		GoToPlanSessions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				PlanSessionsGUI gui= new PlanSessionsGUI(email);
				gui.setVisible(true);
				AdminGUI.this.setVisible(false);
			}
		});
		GoToPlanSessions.setBounds(66, 145, 224, 50);
		getContentPane().add(GoToPlanSessions);
		
		JButton LogOutButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		LogOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI gui= new MainGUI();
				gui.setVisible(true);
				AdminGUI.this.setVisible(false);
			}
		});
		LogOutButton.setBounds(96, 206, 170, 34);
		getContentPane().add(LogOutButton);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Admin"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(76, 41, 214, 14);
		getContentPane().add(lblNewLabel);
	}
}
