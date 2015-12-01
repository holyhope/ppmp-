package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Book extends Remote {

	long getISBN() throws RemoteException;

	long getBarCode() throws RemoteException;

	String getTitle() throws RemoteException;

	String getAuthor() throws RemoteException;

	String getSummary() throws RemoteException;

	String getPublisher() throws RemoteException;

	double getCost() throws RemoteException;

	String getDate() throws RemoteException;

	Comment[] getComments() throws RemoteException;

}