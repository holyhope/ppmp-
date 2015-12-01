package fr.mlv.school;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BookImpl extends UnicastRemoteObject implements Book {
	private static final long serialVersionUID = 1L;
	
	private long ISBN;
	private String title;
	private String author;
	private String summary;
	private String format;
	private Date date;
	private String publisher;
	private final ArrayList<Comment> comments = new ArrayList<>();
	private boolean isAvailable = true;

	public BookImpl(long ISBN, String title, String author, String summary, String format, String publisher,
			int yearPublish, int monthPublish, int dayPublish) throws RemoteException {
		this.ISBN = ISBN;
		this.title = title;
		this.author = author;
		this.summary = summary;
		this.publisher = publisher;
		this.format = format;
		Calendar myCal = Calendar.getInstance();
		myCal.set(Calendar.YEAR, yearPublish);
		myCal.set(Calendar.MONTH, monthPublish);
		myCal.set(Calendar.DAY_OF_MONTH, dayPublish);
		date = myCal.getTime();
	}

	public BookImpl(long ISBN, String title, String author, int yearPublish, int monthPublish, int dayPublish)
			throws RemoteException {
		this(ISBN, title, author, "", "", "", yearPublish, monthPublish, dayPublish);
	}

	public BookImpl(long ISBN, String title, String author) throws RemoteException {
		this(ISBN, title, author, "", "", "", 2000, 01, 01);
	}

	public long getISBN() throws RemoteException {
		return ISBN;
	}

	public String getTitle() throws RemoteException {
		return title;
	}

	public String getAuthor() throws RemoteException {
		return author;
	}

	public void setISBN(long iSBN) throws RemoteException {

		ISBN = iSBN;
	}

	public void setTitle(String title) throws RemoteException {

		this.title = title;
	}

	public void setAuthor(String author) throws RemoteException {
		this.author = author;
	}

	public String getSummary() throws RemoteException {
		return summary;
	}

	public String getFormat() throws RemoteException {
		return format;
	}

	public String getPublisher() throws RemoteException {
		return publisher;
	}

	public String getDate() throws RemoteException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(date);
	}

	public Comment[] getComments() throws RemoteException {
		CommentImpl[] commentsArray = new CommentImpl[comments.size()];
		return comments.toArray(commentsArray);
	}

	public boolean isAvailable() throws RemoteException {
		return isAvailable;
	}

	public void setAvailable() throws RemoteException {
		isAvailable = true;
	}

	public void setNotAvailable() throws RemoteException {
		isAvailable = false;
	}
	
	public String toString(){
		return "Book{ISBN : " + ISBN 
				+ ", title : " + title 
				+ ", author : " + author 
				+ ", summary : " + summary 
				+ ", format : " + format 
				+ ", publisher : " + publisher 
				+ ", Date : " + date.toString() 
				+ ", isAvailable : " + isAvailable + "}"; 
	}
}
