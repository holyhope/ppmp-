package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Observer extends Remote{
	public boolean addNotification(Notification notification) throws RemoteException;

	public List<Notification> getNotifications() throws RemoteException;

	public boolean consumeNotification(Notification notification) throws RemoteException;
}
