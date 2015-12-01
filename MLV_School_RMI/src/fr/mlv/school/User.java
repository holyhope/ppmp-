package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface User extends Remote {
	String getEmail() throws RemoteException;

	String getUsername() throws RemoteException;

	String getRole() throws RemoteException;
}