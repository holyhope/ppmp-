package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.function.Consumer;

public interface User extends Remote {
	public String getEmail() throws RemoteException;

	public String getUsername() throws RemoteException;

	public String getRole() throws RemoteException;

	/*************************/

	public boolean addNotification(Notification notification) throws RemoteException;

	public List<Notification> getNotifications() throws RemoteException;

	public boolean consumeNotification(Notification notification) throws RemoteException;

	public boolean addObserver(Consumer<Notification> consumer) throws RemoteException;
}