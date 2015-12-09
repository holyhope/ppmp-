package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote{
	public void notifyObserver(Book book) throws RemoteException;
}
