package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Users extends Remote {
	public User findByUsername(String username) throws RemoteException;

	public User findByEmail(String email) throws RemoteException;

	public boolean authenticate(User user, String password) throws RemoteException;

	public boolean isRegistered(User user);

	public boolean register(User user, String password) throws RemoteException;
}
