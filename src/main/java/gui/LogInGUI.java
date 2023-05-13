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

public class LogInGUI extends JFrame{
	private JTextField EmailInput;
	private JPasswordField PasswordInput;
	public LogInGUI() {
		setTitle("Login");
		setSize(407, 352);
		getContentPane().setBackground(SystemColor.controlLtHighlight);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LogIn"));
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(126, 10, 141, 54);
		getContentPane().add(lblNewLabel);
		
		JLabel EmailErrorMessage = new JLabel("");
		EmailErrorMessage.setForeground(Color.RED);
		EmailErrorMessage.setFont(new Font("SansSerif", Font.PLAIN, 16));
		EmailErrorMessage.setBounds(57, 135, 301, 14);
		getContentPane().add(EmailErrorMessage);
		
		EmailInput = new JTextField();
		EmailInput.setBackground(Color.WHITE);
		EmailInput.setBounds(57, 103, 301, 24);
		getContentPane().add(EmailInput);
		EmailInput.setColumns(10);
		
		PasswordInput = new JPasswordField();
		PasswordInput.setBounds(57, 201, 301, 19);
		getContentPane().add(PasswordInput);

		JLabel PasswordErrorMessage = new JLabel("");
		PasswordErrorMessage.setForeground(Color.RED);
		PasswordErrorMessage.setFont(new Font("SansSerif", Font.PLAIN, 16));
		PasswordErrorMessage.setBounds(57, 249, 301, 14);
		getContentPane().add(PasswordErrorMessage);
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 24));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(57, 159, 127, 32);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Email");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 24));
		lblNewLabel_1_1.setBounds(52, 61, 74, 32);
		getContentPane().add(lblNewLabel_1_1);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Submit"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String EmailValue = EmailInput.getText();
				String PasswordValue = new String(PasswordInput.getPassword());
				
				// case the user did not provided an email
				if(EmailValue.length() == 0) {
					EmailErrorMessage.setText(ResourceBundle.getBundle("Etiquetas").getString("ProvideEmail"));
				}
				
				// case the user did not provide a password
				else if( PasswordValue.length() == 0) {
					PasswordErrorMessage.setText(ResourceBundle.getBundle("Etiquetas").getString("ProvidePassword"));
				}
				else {
					BLFacade facade = MainGUI.getBusinessLogic();
					Integer userPosNum = facade.login(EmailValue, PasswordValue);
					
					//If the user does not exist
					if(userPosNum==-1) {
						EmailErrorMessage.setText(ResourceBundle.getBundle("Etiquetas").getString("IncorrectEmailOrPassword"));
					}
					
					else{
						//If it is a client
						if(userPosNum==0) {
							UserGUI gui= new UserGUI(EmailValue);
							gui.setVisible(true);
							LogInGUI.this.setVisible(false);
						}
						//If it is an admin
						else {
							AdminGUI gui= new AdminGUI(EmailValue);
							gui.setVisible(true);
							LogInGUI.this.setVisible(false);
						}
					}
				}
			}
		});
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
		btnNewButton.setBounds(209, 249, 149, 44);
		getContentPane().add(btnNewButton);
		
		JButton BackButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI gui= new MainGUI();
				gui.setVisible(true);
				LogInGUI.this.setVisible(false);
			}
		});
		BackButton.setBounds(57, 249, 108, 44);
		getContentPane().add(BackButton);
		
		
		
	}
}
