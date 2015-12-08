package fr.mlv.school;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface Library extends Remote {

	String getName() throws RemoteException;

	boolean addBook(Book book, User user) throws RemoteException;

	boolean deleteBook(Book book, User user) throws RemoteException;

	boolean isBookAvailable(Book book) throws RemoteException;

	boolean isValidUser(User user) throws RemoteException;

	boolean isValidBook(Book book) throws RemoteException;

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

	/**********/

	public User connect(String login, String pass) throws RemoteException;

	public boolean isRegistered(User user) throws RemoteException;

	public boolean register(User currentUser, User user, String password) throws RemoteException;

	public boolean grantPermission(User currentUser, User user, Permission permission) throws RemoteException;

	public boolean revokePermission(User currentUser, User user, Permission permission) throws RemoteException;

	public boolean userCan(User currentUser, User user, Permission permission) throws RemoteException;

	public Set<User> getPermitedUsers(User currentUser, Permission permission) throws RemoteException;

	public Set<Permission> getUserPermissions(User currentUser, User user) throws RemoteException;

	public void disconnect(User user) throws RemoteException;

}