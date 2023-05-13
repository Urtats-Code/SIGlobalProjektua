package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import businessLogic.BLFacade;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class RegisterGUI extends JFrame{
	private JTextField Email;
	private JTextField User;
	private JPasswordField password;
	private JPasswordField passwordField;
	public RegisterGUI() {
		setTitle("Register");
		setSize(427, 356);
		getContentPane().setBackground(SystemColor.controlLtHighlight);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(158, 10, 105, 31);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1_1_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UserName"));
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 24));
		lblNewLabel_1_1_1.setBounds(91, 51, 221, 25);
		getContentPane().add(lblNewLabel_1_1_1);
		
		User = new JTextField();
		User.setColumns(10);
		User.setBackground(Color.WHITE);
		User.setBounds(91, 86, 229, 25);
		getContentPane().add(User);
		
		JLabel UserErrorMessage = new JLabel("");
		UserErrorMessage.setForeground(Color.RED);
		UserErrorMessage.setFont(new Font("SansSerif", Font.PLAIN, 16));
		UserErrorMessage.setBounds(54, 236, 301, 14);
		getContentPane().add(UserErrorMessage);
		
		Email = new JTextField();
		Email.setBackground(Color.WHITE);
		Email.setBounds(91, 150, 229, 25);
		getContentPane().add(Email);
		Email.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 24));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(91, 175, 229, 32);
		getContentPane().add(lblNewLabel_1);
		
		JLabel EmailErrorMessage = new JLabel("");
		EmailErrorMessage.setForeground(new Color(255, 0, 0));
		EmailErrorMessage.setFont(new Font("SansSerif", Font.PLAIN, 16));
		EmailErrorMessage.setBounds(91, 236, 229, 14);
		getContentPane().add(EmailErrorMessage);
		
		JLabel PasswordErrorMessage = new JLabel("");
		PasswordErrorMessage.setForeground(Color.RED);
		PasswordErrorMessage.setFont(new Font("SansSerif", Font.PLAIN, 16));
		PasswordErrorMessage.setBounds(91, 371, 301, 14);
		getContentPane().add(PasswordErrorMessage);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(91, 207, 229, 19);
		getContentPane().add(passwordField);
		
		JLabel lblNewLabel_1_1 = new JLabel("Email");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 24));
		lblNewLabel_1_1.setBounds(91, 123, 229, 21);
		getContentPane().add(lblNewLabel_1_1);
		
		JButton SubmitButton = new JButton("Submit");
		SubmitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String EmailValue = Email.getText();
				String PasswordValue = new String(passwordField.getPassword());
				String UserValue = User.getText();
				
				// reset errors 
				EmailErrorMessage.setText("");
				PasswordErrorMessage.setText("");
				
				boolean validUser = true;
				BLFacade facade = MainGUI.getBusinessLogic();
				
				// case the user did not provided an email
				if(UserValue.length() == 0) {
					UserErrorMessage.setText("You have to provide an email");
					validUser = false;
				}
				
				// case the user did not provided an email
				if(EmailValue.length() == 0) {
					EmailErrorMessage.setText("You have to provide an email");
					validUser = false;
				}
				
				// case the user did not provide a password
				if( PasswordValue.length() == 0) {
					PasswordErrorMessage.setText("You have to provide a password");
					validUser = false;
				}				
				
				// check if the user exists in the data base
				boolean resgitered = facade.register(UserValue, EmailValue, PasswordValue);
				if(!resgitered) {
					EmailErrorMessage.setText("The provided user already exists");
					validUser = false;
				}
				
				// validation completed 				
				if(validUser) {
					UserGUI ui = new UserGUI(EmailValue);
					ui.setVisible(true);
					RegisterGUI.this.setVisible(false);
				}
			}
		});
		
		
		SubmitButton.setForeground(new Color(0, 0, 0));
		SubmitButton.setBackground(new Color(255, 255, 255));
		SubmitButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
		SubmitButton.setBounds(191, 260, 149, 44);
		getContentPane().add(SubmitButton);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI gui= new MainGUI();
				gui.setVisible(true);
				RegisterGUI.this.setVisible(false);
			}
		});
		btnNewButton.setBounds(54, 260, 105, 44);
		getContentPane().add(btnNewButton);
		
		
		
		
		

	}
}
