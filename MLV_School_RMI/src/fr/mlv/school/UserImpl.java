package fr.mlv.school;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserImpl extends UnicastRemoteObject implements User {
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String email;
	private String role;

	public UserImpl(String userName, String email, String role) throws RemoteException {
		this.userName = userName;
		this.email = email;
		this.role = role;
	}
	
	public UserImpl() throws RemoteException{
		this("","","undefined");
	}

	public String getEmail() throws RemoteException {
		return email;
	}

	public void setEmail(String email) throws RemoteException {
		this.email = email;
	}

	public String getUserName() throws RemoteException {
		return userName;
	}

	public String getRole() throws RemoteException {
		return role;
	}
}
