package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface Comment extends Remote {

	String getAuthor() throws RemoteException;

	int getMark() throws RemoteException;

	String getContent() throws RemoteException;

	Date getDate() throws RemoteException;

}