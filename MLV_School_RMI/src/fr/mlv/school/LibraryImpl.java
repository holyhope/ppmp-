package fr.mlv.school;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class LibraryImpl extends UnicastRemoteObject implements Library {
	private static final long serialVersionUID = 1L;
	
	private final String name = "MLV-School";
	private final ConcurrentHashMap<Long, Book> library = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<User, ArrayList<Book>> borrowers = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Book, ArrayBlockingQueue<User>> waitingList = new ConcurrentHashMap<>();

	public LibraryImpl() throws RemoteException {
	}

	public String getName() throws RemoteException {
		return name;
	}

	public boolean addBook(Book book, User user) throws RemoteException {
		if (user.getRole().equals("teacher")) {
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
			return true;
		}
		return false;
	}

	public boolean isBookAvailable(Book book) throws RemoteException {
		return book.isAvailable();
	}

	public boolean getBook(Book book, User user) throws RemoteException {
		if (book.isAvailable()) {
			book.setNotAvailable();
			ArrayList<Book> borrowerBooks = borrowers.getOrDefault(user, new ArrayList<Book>());
			borrowerBooks.add(book);
			borrowers.put(user, borrowerBooks);
			return true;
		}
		return false;
	}

	public boolean restoreBook(Book book, User user) throws RemoteException {
		book.setAvailable();
		ArrayList<Book> borrowerBooks = borrowers.get(user);
		if (borrowerBooks != null) {
			if (borrowerBooks.remove(book)) {
				borrowers.put(user, borrowerBooks);
				return true;
			}
		}
		return false;
	}

	public boolean subscribeToWaitingList(Book book, User user) throws RemoteException {
		if (!book.isAvailable()) {
			ArrayBlockingQueue<User> usersWaiting = waitingList.getOrDefault(book,
					new ArrayBlockingQueue<User>(3));
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

	public Book searchByISBN(long ISBN) throws RemoteException {
		return library.get(ISBN);
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
}
