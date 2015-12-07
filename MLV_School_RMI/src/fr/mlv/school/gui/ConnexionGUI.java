package fr.mlv.school.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fr.mlv.school.Library;
import fr.mlv.school.User;

public class ConnexionGUI {
	private final Library					library;

	private final JFrame					frame		  = new JFrame();
	private final JPasswordField			passwordField = new JPasswordField();;
	private final JTextField				loginField	  = new JTextField();
	private User							user;

	private final ArrayList<Consumer<User>>	consumers	  = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// ConnexionGUI window = new ConnexionGUI();
	// window.frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	private ConnexionGUI(Library library) {
		this.library = library;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public static ConnexionGUI construct(Library library) {
		ConnexionGUI connexionGUI = new ConnexionGUI(library);

		try {
			connexionGUI.frame.setTitle("Connexion à " + library.getName());
		} catch (RemoteException e) {
			e.printStackTrace(System.err);
			connexionGUI.frame.setTitle("Connexion à une biliothèque inconnue");
		}
		connexionGUI.frame.setSize(312, 194);
		connexionGUI.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connexionGUI.frame.setLocationRelativeTo(null);
		connexionGUI.frame.setResizable(false);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(218, 165, 32));
		connexionGUI.frame.getContentPane().add(desktopPane, BorderLayout.CENTER);

		JLabel lblConnexion = new JLabel("Login");
		lblConnexion.setBounds(18, 22, 61, 16);
		desktopPane.add(lblConnexion);

		JLabel lblPassWord = new JLabel("Mot de passe");
		lblPassWord.setBounds(18, 69, 112, 16);
		desktopPane.add(lblPassWord);

		connexionGUI.passwordField.setBounds(155, 63, 133, 28);
		desktopPane.add(connexionGUI.passwordField);

		connexionGUI.loginField.setBounds(154, 16, 134, 28);
		desktopPane.add(connexionGUI.loginField);
		connexionGUI.loginField.setColumns(10);

		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String login = connexionGUI.loginField.getText();
				String pass = connexionGUI.passwordField.getText();
				try {
					try {
						User user = library.findByUsername(login);
						if (library.authenticate(user, pass)) {
							connexionGUI.consumers.parallelStream().forEach(c -> c.accept(user));
							System.out.println("Tout va bien");
						} else {
							connexionGUI.wizz();
						}
					} catch (IllegalArgumentException e) {
						System.err.println("Cannot authenticate: " + e.getMessage());
						connexionGUI.wizz();
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnConnexion.setBounds(18, 120, 270, 29);
		desktopPane.add(btnConnexion);

		connexionGUI.frame.setVisible(true);

		return connexionGUI;
	}

	public void wizz() {
		Point point = frame.getLocation();
		for (int i = 0; i < 7; i++) {
			for (int j = 1; j < 20; j++) {
				frame.setLocation((int) (point.x + (j * Math.pow(-1, i % 2))), point.y);
			}
			try {
				Thread.sleep(80);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void addConnexionListener(Consumer<User> consumer) {
		consumers.add(consumer);
	}

	public void close() {
		this.frame.dispose();
	}
}
