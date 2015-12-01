package fr.mlv.school;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class LibraryImpl extends UnicastRemoteObject implements Library {
	private static final long serialVersionUID = 1L;

	private final String name = "MLV-School";
	private final ConcurrentHashMap<Long, Book> library = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Book, User> borrowers = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Book, ArrayBlockingQueue<User>> waitingList = new ConcurrentHashMap<>();
	private final CopyOnWriteArrayList<History> histories = new CopyOnWriteArrayList<>();
	private final ConcurrentHashMap<Long, Long> bookRegistered = new ConcurrentHashMap<>();

	public LibraryImpl() throws RemoteException {
	}

	public String getName() throws RemoteException {
		return name;
	}

	public boolean addBook(Book book, User user) throws RemoteException {
		if (user.getRole().equals("teacher")) {
			bookRegistered.put(book.getBarCode(), System.currentTimeMillis());
			library.put(book.getISBN(), book);
			return true;
		}
		return false;
	}

	/*
	 * public boolean deleteBook(long ISBN, User user) { if
	 * (user.getRole().equals(Role.teacher)) { library.remove(ISBN); return
	 * true; } return false; }
	 */

	public boolean deleteBook(Book book, User user) throws RemoteException {
		if (user.getRole().equals("teacher")) {
			library.remove(book.getISBN());
			bookRegistered.remove(book.getBarCode());
			return true;
		}
		return false;
	}

	public boolean isBookAvailable(Book book) throws RemoteException {
		return borrowers.get(book) == null ? true : false;
	}

	public boolean getBook(Book book, User user) throws RemoteException {
		if (borrowers.get(book) == null) {
			histories.add(new History(book, user, 1));
			borrowers.put(book, user);
			return true;
		}
		return false;
	}

	public boolean restoreBook(Book book, User user) throws RemoteException {
		if (borrowers.remove(book) == null) {
			histories.add(new History(book, user, 2));
			return false;
		}
		return true;

	}

	public boolean subscribeToWaitingList(Book book, User user) throws RemoteException {
		if (borrowers.get(book) != null) {
			ArrayBlockingQueue<User> usersWaiting = waitingList.getOrDefault(book, new ArrayBlockingQueue<User>(3));
			try {
				usersWaiting.add(user);
			} catch (IllegalStateException ise) {
				return false;
			}
			waitingList.put(book, usersWaiting);
			return true;
		}
		return false;
	}

	public boolean unsubscribeToWaitingList(Book book, User user) throws RemoteException {
		ArrayBlockingQueue<User> usersWaiting = waitingList.get(book);
		if (usersWaiting != null) {
			if (usersWaiting.remove(user)) {
				waitingList.put(book, usersWaiting);
				return true;
			}
		}
		return false;
	}

	public Book searchByBarCode(long barCode) throws RemoteException {
		return library.get(barCode);
	}

	public Book[] searchByISBN(long ISBN) throws RemoteException {
		ArrayList<Book> books = new ArrayList<>();
		for (Book book : library.values()) {
			if (book.getISBN() == ISBN) {
				books.add(book);
			}
		}
		Book[] booksArray = new Book[books.size()];
		booksArray = books.toArray(booksArray);
		return booksArray;
	}

	public Book[] searchByTitle(String title) throws RemoteException {
		ArrayList<Book> books = new ArrayList<>();
		for (Book book : library.values()) {
			if (book.getTitle().contains(title)) {
				books.add(book);
			}
		}
		Book[] booksArray = new Book[books.size()];
		booksArray = books.toArray(booksArray);
		return booksArray;
	}

	public Book[] searchByAuthor(String author) throws RemoteException {
		ArrayList<Book> books = new ArrayList<>();
		for (Book book : library.values()) {
			if (book.getAuthor().contains(author)) {
				books.add(book);
			}
		}
		Book[] booksArray = new Book[books.size()];
		booksArray = books.toArray(booksArray);
		return booksArray;
	}

	public boolean isBuyable(Book book) throws RemoteException {
		long timestamp = bookRegistered.get(book.getBarCode());
		Date date = new Date(timestamp);
		Date currentDate = new Date();
		int diffInDays = (int) ((currentDate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
		if (borrowers.get(book) == null) {
			if (diffInDays > 365 * 2) {
				for (History history : histories) {
					if (history.getType() == 1 && history.getBook().equals(book)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean buyBook(Book book, User user) throws RemoteException {
		if (isBuyable(book)) {
			library.remove(book.getBarCode());
			histories.add(new History(book, user, 3));
			return true;
		}
		return false;
	}
	
	public double getCost(Book book) throws RemoteException{
		return book.getCost();
	}

}
