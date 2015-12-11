package fr.mlv.school;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import fr.mlv.school.gui.BiblioGUI;
import fr.mlv.school.gui.ConnexionGUI;
import fr.mlv.school.gui.Theme;

@SuppressWarnings("deprecation")
public class LibraryClient {
	public static void main(String[] args) {
		try {
			// System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "sec.policy");
			System.setSecurityManager(new RMISecurityManager());

			String serviceName = "rmi://localhost:1099/LibraryService";
			if (args.length == 1) {
				serviceName = args[0];
			}
			Library library = (Library) Naming.lookup(serviceName);

			Theme theme = new Theme();

			try {
				ConnexionGUI connexionGUI = ConnexionGUI.construct(theme, library);
				connexionGUI.addConnectedListener(user -> {
					try {
						BiblioGUI biblioGUI = BiblioGUI.construct(theme, library, user);
						biblioGUI.addCloseListener(event -> {
							try {
								library.disconnect(user);
							} catch (Exception e) {
								e.printStackTrace(System.err);
							}
						});
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
}
