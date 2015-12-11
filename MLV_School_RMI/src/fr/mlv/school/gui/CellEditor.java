package fr.mlv.school.gui;

import java.awt.Component;
import java.util.EventObject;
import java.util.function.Function;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import fr.mlv.school.Book;
import fr.mlv.school.gui.table.CellRenderer;

@SuppressWarnings("serial")
public class CellEditor extends DefaultCellEditor implements TableCellEditor {
	private final Theme					 theme;
	private final BookCard				 bookCard;
	private final Function<Book, String> function;

	private Book						 book;

	private CellEditor(Theme theme, BookCard bookCard, Function<Book, String> function) {
		super(new JCheckBox());
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
		return false;
	}

	@Override
	public Object getCellEditorValue() {
		return book;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		System.out.println("edition...");
		// TODO
		return CellRenderer.construct(theme, bookCard, function).getTableCellRendererComponent(table, value, isSelected,
				false, row, column);
	}

}
