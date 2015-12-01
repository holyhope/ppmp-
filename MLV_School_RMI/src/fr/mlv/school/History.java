package fr.mlv.school;

public class History {
	private final Book book;
	private final User borrower;
	private final long date;
	private final int type;
	
	public History(Book book, User borrower, int type){
		this.book = book;
		this.borrower = borrower;
		this.type = type;
		this.date = System.currentTimeMillis();
	}

	public Book getBook() {
		return book;
	}

	public User getBorrower() {
		return borrower;
	}

	public long getDate() {
		return date;
	}

	public int getType() {
		return type;
	}
}
