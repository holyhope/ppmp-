package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Comment extends Remote {

	String getAuthor() throws RemoteException;

	int getMark() throws RemoteException;

	String getContent() throws RemoteException;

	long getTime() throws RemoteException;

}