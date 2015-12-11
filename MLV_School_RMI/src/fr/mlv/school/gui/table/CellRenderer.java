package fr.mlv.school.gui.table;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.function.Function;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import fr.mlv.school.Book;
import fr.mlv.school.gui.BookCard;
import fr.mlv.school.gui.Theme;

@SuppressWarnings("serial")
public class CellRenderer extends JLabel implements TableCellRenderer, MouseListener {
	private final Theme					 theme;
	private final BookCard				 bookCard;

	private final Function<Book, String> function;

	private Book						 book;

	private CellRenderer(Theme theme, BookCard bookCard, Function<Book, String> function) {
		this.theme = theme;
		this.bookCard = bookCard;
		this.function = function;
	}

	public static CellRenderer construct(Theme theme, BookCard bookCard, Function<Book, String> function) {
		CellRenderer label = new CellRenderer(theme, bookCard, function);
		label.addMouseListener(label);
		return label;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (isSelected) {
			theme.applySelectedTo(this);
		} else {
			theme.applyTo(this);
		}

		book = (Book) value;

		setText(function.apply(book));

		return this;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("clicked");
		try {
			bookCard.show(book);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(System.err);
		}
	}
}
