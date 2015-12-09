package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface User extends Remote {
	public String getEmail() throws RemoteException;

	public String getUsername() throws RemoteException;

	public String getRole() throws RemoteException;

	/*************************/

	public boolean addNotification(Notification notification) throws RemoteException;

	public List<Notification> getNotifications() throws RemoteException;

	public boolean consumeNotification(Notification notification) throws RemoteException;

	public boolean addObserver(Observer observer) throws RemoteException;
}