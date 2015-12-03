package fr.mlv.school.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class ConnexionGUI {

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnexionGUI window = new ConnexionGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConnexionGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(300, 200);
		//frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(218, 165, 32));
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JLabel lblConnexion = new JLabel("Login");
		lblConnexion.setBounds(18, 22, 61, 16);
		desktopPane.add(lblConnexion);
		
		JLabel lblPassWord = new JLabel("Mot de passe");
		lblPassWord.setBounds(18, 69, 112, 16);
		desktopPane.add(lblPassWord);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(155, 63, 133, 28);
		desktopPane.add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(154, 16, 134, 28);
		desktopPane.add(textField);
		textField.setColumns(10);
		
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.setBounds(18, 120, 270, 29);
		desktopPane.add(btnConnexion);
	}
}