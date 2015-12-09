package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface Library extends Remote {

	String getName() throws RemoteException;

	boolean addBook(Book book, User user) throws RemoteException;

	boolean deleteBook(Book book, User user) throws RemoteException;

	boolean isBookAvailable(Book book) throws RemoteException;

	void checkValidUser(User user) throws RemoteException;

	void checkValidBook(Book book) throws RemoteException;

	boolean getBook(Book book, User user) throws RemoteException;

	boolean restoreBook(Book book, User user) throws RemoteException;

	boolean subscribeToWaitingList(Book book, User user) throws RemoteException;

	boolean unsubscribeToWaitingList(Book book, User user) throws RemoteException;

	Book searchByBarCode(long barCode) throws RemoteException;

	Book[] searchByISBN(long ISBN) throws RemoteException;

	Book[] searchByTitle(String title) throws RemoteException;

	Book[] searchByAuthor(String author) throws RemoteException;

	boolean isBuyable(Book book) throws RemoteException;

	boolean buyBook(Book book, User user) throws RemoteException;

	double getCost(Book book) throws RemoteException;

	Book[] getAllBooks() throws RemoteException;

	/**********/

	public User connect(String login, char[] pass) throws RemoteException;

	public boolean isRegistered(User user) throws RemoteException;

	public void disconnect(User user) throws RemoteException;

}