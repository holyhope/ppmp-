package fr.mlv.school;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BookImpl extends UnicastRemoteObject implements Book {
	private static final long		 serialVersionUID = 1L;

	private final long				 barCode;

	private final long				 ISBN;
	private final String			 title;
	private final String			 author;
	private final String			 summary;
	private final String			 format;
	private final Date				 date;
	private final String			 publisher;
	private final double			 cost;
	private final ArrayList<Comment> comments		  = new ArrayList<>();

	public BookImpl(long ISBN, long barCode, String title, String author, String summary, String format,
			String publisher, double cost, int yearPublish, int monthPublish, int dayPublish) throws RemoteException {
		this.ISBN = ISBN;
		this.barCode = barCode;
		this.title = title;
		this.author = author;
		this.summary = summary;
		this.publisher = publisher;
		this.format = format;
		this.cost = cost;
		Calendar myCal = Calendar.getInstance();
		myCal.set(Calendar.YEAR, yearPublish);
		myCal.set(Calendar.MONTH, monthPublish);
		myCal.set(Calendar.DAY_OF_MONTH, dayPublish);
		date = myCal.getTime();
	}

	public long getISBN() throws RemoteException {
		return ISBN;
	}

	public long getBarCode() throws RemoteException {
		return barCode;
	}

	public String getTitle() throws RemoteException {
		return title;
	}

	public String getAuthor() throws RemoteException {
		return author;
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

	public double getCost() throws RemoteException {
		return cost;
	}

	public String getDate() throws RemoteException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(date);
	}

	public Comment[] getComments() throws RemoteException {
		CommentImpl[] commentsArray = new CommentImpl[comments.size()];
		return comments.toArray(commentsArray);
	}

	public String toString() {
		return "Book{ISBN : " + ISBN + ", BarCode : " + barCode + ", Title : " + title + ", Author : " + author
				+ ", Summary : " + summary + ", Format : " + format + ", Publisher : " + publisher + ", Cost : " + cost
				+ ", Date : " + date.toString() + "}";
	}
}
