package fr.mlv.school;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class LibraryServer {
	public static void main(String[] args) {
		try {
			Library library = new LibraryImpl();
			for(int i = 0; i < 20; i++){
				Book b = new BookImpl((i+1),(i+1),  "Titre " + i, "Auteur " + i, "Resume " + i, "Editeur " + i,i*10., 2015, 12, (i + 1));
				library.addBook(b, new UserImpl("Maxime", "maxime@wanadoo.fr", "teacher"));
			}
			
			for(int i = 0; i < 20; i++){
				Book b = library.searchByBarCode(i);
				System.out.println(b);
			}
			
			//String codebase = "file:///Users/Maxime/git/ppmp-master/MLV_School_RMI/src";
			//System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "sec.policy");
			System.setSecurityManager(new RMISecurityManager());
			Naming.rebind("rmi://localhost:1099/LibraryService", library);
			System.out.println("Server is Ready.");
			
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		catch(MalformedURLException mue){
			mue.printStackTrace();
		}
	}
}
