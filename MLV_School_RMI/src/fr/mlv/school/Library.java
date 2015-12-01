package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Library extends Remote {

	String getName() throws RemoteException;

	boolean addBook(Book book, User user) throws RemoteException;

	boolean deleteBook(Book book, User user) throws RemoteException;

	boolean isBookAvailable(Book book) throws RemoteException;

	boolean getBook(Book book, User user) throws RemoteException;

	boolean restoreBook(Book book, User user) throws RemoteException;

	boolean subscribeToWaitingList(Book book, User user) throws RemoteException;

	boolean unsubscribeToWaitingList(Book book, User user) throws RemoteException;

	Book searchByISBN(long ISBN) throws RemoteException;

	Book[] searchByTitle(String title) throws RemoteException;

	Book[] searchByAuthor(String author) throws RemoteException;

}