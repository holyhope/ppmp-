package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Book extends Remote {

	long getISBN() throws RemoteException;

	String getTitle() throws RemoteException;

	String getAuthor() throws RemoteException;

	void setISBN(long iSBN) throws RemoteException;

	void setTitle(String title) throws RemoteException;

	void setAuthor(String author) throws RemoteException;

	String getSummary() throws RemoteException;

	String getFormat() throws RemoteException;

	String getPublisher() throws RemoteException;

	String getDate() throws RemoteException;

	Comment[] getComments() throws RemoteException;

	boolean isAvailable() throws RemoteException;

	void setAvailable() throws RemoteException;

	void setNotAvailable() throws RemoteException;

}