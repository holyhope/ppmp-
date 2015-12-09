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
	private final Date				 date;
	private final String			 publisher;
	private final double			 cost;
	private final ArrayList<Comment> comments		  = new ArrayList<>();

	public BookImpl(long ISBN, long barCode, String title, String author, String summary, String publisher, double cost,
			int yearPublish, int monthPublish, int dayPublish) throws RemoteException {
		if (ISBN <= 0) {
			throw new IllegalArgumentException("ISBN is not valid");
		}
		this.ISBN = ISBN;

		if (barCode <= 0) {
			throw new IllegalArgumentException("barCode is not valid");
		}
		this.barCode = barCode;

		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException("title is not valid");
		}
		this.title = title;

		if (author == null || author.isEmpty()) {
			throw new IllegalArgumentException("author is not valid");
		}
		this.author = author;

		if (summary == null) {
			throw new IllegalArgumentException("summary is not valid");
		}
		this.summary = summary;

		if (publisher == null || publisher.isEmpty()) {
			throw new IllegalArgumentException("publisher is not valid");
		}
		this.publisher = publisher;

		if (cost <= 0) {
			throw new IllegalArgumentException("cost is not valid");
		}
		this.cost = (int) (cost * 100) / 100.;

		Calendar myCal = Calendar.getInstance();
		myCal.set(Calendar.YEAR, yearPublish);
		// Do not change: Months start with index 0
		myCal.set(Calendar.MONTH, monthPublish - 1);
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
				+ ", Summary : " + summary + ", Publisher : " + publisher + ", Cost : " + cost + ", Date : "
				+ date.toString() + "}";
	}
}
