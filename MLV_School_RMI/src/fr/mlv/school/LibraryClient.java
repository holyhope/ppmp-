package fr.mlv.school;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import fr.mlv.school.gui.BiblioGUI;
import fr.mlv.school.gui.ConnexionGUI;

public class LibraryClient {
	public static void main(String[] args) {
		try {
			// System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "sec.policy");
			System.setSecurityManager(new RMISecurityManager());
			Library library = (Library) Naming.lookup("rmi://localhost:1099/LibraryService");

			try {
				ConnexionGUI connexionGUI = ConnexionGUI.construct(library);
				connexionGUI.addConnexionListener(user -> {
					BiblioGUI biblioGUI = BiblioGUI.construct(library, user);
					connexionGUI.close();
					System.out.println("connexion");
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}
