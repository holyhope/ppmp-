package fr.mlv.school;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class LibraryServer {
	public static void main(String[] args) {
		try {
			Users users = new UsersImpl();

			UserImpl adminUser = new UserImpl("Admin", "admin@u-pem.fr", "admin");
			users.register(adminUser, "adminuser");
			users.grantPermission(adminUser, Permission.ADD_BOOK);
			users.grantPermission(adminUser, Permission.REMOVE_BOOK);

			Library library = new LibraryImpl(users);

			for (int i = 1; i < 20; i++) {
				Book b = new BookImpl((i + 1), (i + 1), "Titre " + i, "Auteur " + i, "Resume " + i, "Editeur " + i,
						i * 10., 2015, 12, (i + 1));
				library.addBook(b, adminUser);
			}

			// String codebase =
			// "file:///Users/Maxime/git/ppmp-master/MLV_School_RMI/src";
			// System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "sec.policy");
			System.setSecurityManager(new RMISecurityManager());
			Naming.rebind("rmi://localhost:1099/LibraryService", library);
			System.out.println("Server is Ready.");

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		}
	}
}
