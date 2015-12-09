package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Notification extends Remote {
	public String getMessage() throws RemoteException;

	public String getTitle();
}
