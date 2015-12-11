package fr.mlv.school.gui;

import java.awt.Component;
import java.util.EventObject;
import java.util.function.Function;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import fr.mlv.school.Book;
import fr.mlv.school.gui.table.CellRenderer;

public class CellEditor implements TableCellEditor {
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
	public Object getCellEditorValue() {
		return book;
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean stopCellEditing() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void cancelCellEditing() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		book = (Book) value;
		System.out.println("edition...");
		// TODO
		return CellRenderer.construct(theme, bookCard, function);
	}

}
