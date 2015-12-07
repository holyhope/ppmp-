package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface User extends Remote {
	public String getEmail() throws RemoteException;

	public String getUsername() throws RemoteException;

	public String getRole() throws RemoteException;
}