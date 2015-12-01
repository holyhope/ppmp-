package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface User extends Remote{

	String getEmail() throws RemoteException;

	void setEmail(String email) throws RemoteException;

	String getUserName() throws RemoteException;

	String getRole() throws RemoteException;

}