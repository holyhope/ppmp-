package fr.mlv.school.gui;

import java.awt.Component;
import java.rmi.RemoteException;
import java.util.EventObject;
import java.util.function.Function;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import fr.mlv.school.Book;
import fr.mlv.school.gui.table.CellRenderer;

@SuppressWarnings("serial")
public class CellEditor extends AbstractCellEditor implements TableCellEditor {
	private final Theme					 theme;
	private final BookCard				 bookCard;
	private final Function<Book, String> function;

	private Book						 book;

	private CellEditor(Theme theme, BookCard bookCard, Function<Book, String> function) {
		this.theme = theme;
		this.bookCard = bookCard;
		this.function = function;
	}

	public static CellEditor construct(Theme theme, BookCard bookCard, Function<Book, String> function) {
		CellEditor editor = new CellEditor(theme, bookCard, function);
		return editor;
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	@Override
	public Object getCellEditorValue() {
		return book;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		book = (Book) value;
		try {
			bookCard.show(book);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CellRenderer.construct(theme, function).getTableCellRendererComponent(table, value, true, false, row,
				column);
	}

}
