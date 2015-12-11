package fr.mlv.school.gui;

import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import fr.mlv.school.Book;

public class BookCard {
	private final JFrame frame			= new JFrame();

	private final JLabel isbnLabel		= new JLabel();
	private final JLabel titleLabel		= new JLabel();
	private final JLabel authorLabel	= new JLabel();
	private final JLabel publisherLabel	= new JLabel();
	private final JLabel summaryLabel	= new JLabel();
	private final JLabel costLabel		= new JLabel();

	private BookCard() {
	}

	public static BookCard construct(Theme theme) {
		BookCard bookCard = new BookCard();
		bookCard.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		bookCard.frame.setSize(400, 800);

		bookCard.frame.add(theme.applyTo(new JLabel("ISBN")));
		bookCard.frame.add(bookCard.isbnLabel);

		bookCard.frame.add(theme.applyTo(new JLabel("Titre")));
		bookCard.frame.add(bookCard.titleLabel);

		bookCard.frame.add(theme.applyTo(new JLabel("Auteur")));
		bookCard.frame.add(bookCard.authorLabel);

		bookCard.frame.add(theme.applyTo(new JLabel("Éditeur")));
		bookCard.frame.add(bookCard.publisherLabel);

		bookCard.frame.add(theme.applyTo(new JLabel("Prix")));
		bookCard.frame.add(bookCard.costLabel);

		bookCard.frame.add(theme.applyTo(new JLabel("Résumé")));
		bookCard.frame.add(bookCard.summaryLabel);

		return bookCard;
	}

	public void show(Book book) throws RemoteException {
		isbnLabel.setText(Long.toString(book.getISBN()));
		titleLabel.setText(book.getTitle());
		authorLabel.setText(book.getAuthor());
		publisherLabel.setText(book.getPublisher());
		summaryLabel.setText(book.getAuthor());
		costLabel.setText(book.getPublisher());
		frame.setVisible(true);
	}
}
