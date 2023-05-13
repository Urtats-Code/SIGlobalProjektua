package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTextField;

import businessLogic.BLFacade;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class CreateActivitiesGUI extends JFrame{
	private JTextField ActivityNameField;
	private JTextField ActivityLevelField;
	public CreateActivitiesGUI(String email) {
		setTitle("User");
		setSize(495, 290);
		getContentPane().setLayout(null);
		
		JLabel Label = new JLabel("Email: ");
		Label.setBounds(10, 11, 55, 14);
		Label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		getContentPane().add(Label);
		
		JLabel UserEmail = new JLabel(email);
		UserEmail.setBounds(66, 11, 380, 14);
		UserEmail.setFont(new Font("SansSerif", Font.PLAIN, 18));
		getContentPane().add(UserEmail);
		
		JLabel ActivityNameLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ActivityName"));
		ActivityNameLabel.setBounds(36, 63, 115, 13);
		getContentPane().add(ActivityNameLabel);
		
		JLabel ActivityLevelLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ActivityLevel"));
		ActivityLevelLabel.setBounds(36, 110, 96, 13);
		getContentPane().add(ActivityLevelLabel);
		
		ActivityNameField = new JTextField();
		ActivityNameField.setBounds(185, 60, 140, 19);
		getContentPane().add(ActivityNameField);
		ActivityNameField.setColumns(10);
		
		ActivityLevelField = new JTextField();
		ActivityLevelField.setBounds(185, 107, 140, 19);
		getContentPane().add(ActivityLevelField);
		ActivityLevelField.setColumns(10);
		
		JLabel ErrorLabel = new JLabel("");
		ErrorLabel.setBounds(156, 149, 45, 13);
		getContentPane().add(ErrorLabel);
		
		JButton SubmitButton = new JButton("Submit");
		SubmitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String activityName=ActivityNameField.getText();
				try{
					Integer activityLevel = Integer.parseInt(ActivityLevelField.getText());
					if(activityLevel<1 || activityLevel>5) {
						ErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("RespectLevelBoundaries"));
					}
					else {
						BLFacade facade = MainGUI.getBusinessLogic();
						boolean exists = facade.saveActivity(activityName, activityLevel);
						if(!exists)
							ErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ActivityAlreadyExists"));
						else
							ErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("WellAdded"));	
					}
				}
				catch(NumberFormatException e1) {
					ErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("MustBeInteger"));
				}
			}
		});
		SubmitButton.setBounds(188, 172, 85, 21);
		getContentPane().add(SubmitButton);
		
		JButton BackButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminGUI gui= new AdminGUI(email);
				gui.setVisible(true);
				CreateActivitiesGUI.this.setVisible(false);
			}
		});
		BackButton.setBounds(66, 172, 85, 21);
		getContentPane().add(BackButton);
		
		
	}
}
