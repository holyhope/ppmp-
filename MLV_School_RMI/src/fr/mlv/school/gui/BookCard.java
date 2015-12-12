package fr.mlv.school.gui;

import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

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
		theme.applyTo(bookCard.frame);
		int frameWidth = 600;
		int frameHeight = 400;
		int i = 0;
		bookCard.frame.setResizable(false);
		bookCard.frame.setSize(frameWidth, frameHeight);
		bookCard.frame.setLayout(null);

		JPanel isbnPanel = theme.applyTo(new JPanel());
		isbnPanel.setBounds(0, i++ * 30, frameWidth, 30);
		isbnPanel.add(theme.applyTo(new JLabel("ISBN")));
		isbnPanel.add(theme.applyTo(bookCard.isbnLabel));
		bookCard.frame.add(isbnPanel);

		JPanel titlePanel = theme.applyTo(new JPanel());
		titlePanel.setBounds(0, i++ * 30, frameWidth, 30);
		titlePanel.add(theme.applyTo(new JLabel("Titre")));
		titlePanel.add(theme.applyTo(bookCard.titleLabel));
		bookCard.frame.add(titlePanel);

		JPanel authorPanel = theme.applyTo(new JPanel());
		authorPanel.setBounds(0, i++ * 30, frameWidth, 30);
		authorPanel.add(theme.applyTo(new JLabel("Auteur")));
		authorPanel.add(theme.applyTo(bookCard.authorLabel));
		bookCard.frame.add(authorPanel);

		JPanel publisherPanel = theme.applyTo(new JPanel());
		publisherPanel.setBounds(0, i++ * 30, frameWidth, 30);
		publisherPanel.add(theme.applyTo(new JLabel("Éditeur")));
		publisherPanel.add(theme.applyTo(bookCard.publisherLabel));
		bookCard.frame.add(publisherPanel);

		JPanel costPanel = theme.applyTo(new JPanel());
		costPanel.setBounds(0, i++ * 30, frameWidth, 30);
		costPanel.add(theme.applyTo(new JLabel("Prix")));
		costPanel.add(theme.applyTo(bookCard.costLabel));
		bookCard.frame.add(costPanel);

		JPanel summaryPanel = theme.applyTo(new JPanel());
		summaryPanel.setBounds(0, i++ * 30, frameWidth, 30);
		summaryPanel.add(theme.applyTo(new JLabel("Résumé")));
		bookCard.frame.add(summaryPanel);

		int y = i++ * 30;
		bookCard.summaryLabel.setBounds(0, y, frameWidth, frameHeight - y);
		bookCard.summaryLabel.setBackground(theme.background);
		bookCard.summaryLabel.setOpaque(true);
		bookCard.summaryLabel.setVerticalAlignment(JLabel.TOP);
		bookCard.summaryLabel
				.setBorder(new CompoundBorder(bookCard.summaryLabel.getBorder(), new EmptyBorder(10, 10, 10, 10)));
		bookCard.frame.add(theme.applyTo(bookCard.summaryLabel));

		return bookCard;
	}

	public void show(Book book) throws RemoteException {
		isbnLabel.setText(Long.toString(book.getISBN()));
		titleLabel.setText(book.getTitle());
		authorLabel.setText(book.getAuthor());
		publisherLabel.setText(book.getPublisher());
		summaryLabel.setText("<html>" + book.getSummary() + "</html>");
		costLabel.setText(Double.toString(book.getCost()));
		frame.setVisible(true);
	}
}
