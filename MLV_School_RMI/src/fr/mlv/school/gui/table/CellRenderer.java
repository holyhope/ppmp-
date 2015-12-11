package fr.mlv.school.gui.table;

import java.awt.Component;
import java.util.function.Function;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import fr.mlv.school.Book;
import fr.mlv.school.gui.Theme;

@SuppressWarnings("serial")
public class CellRenderer extends JLabel implements TableCellRenderer {
	private final Theme					 theme;

	private final Function<Book, String> function;

	private Book						 book;

	private CellRenderer(Theme theme, Function<Book, String> function) {
		this.theme = theme;
		this.function = function;
	}

	public static CellRenderer construct(Theme theme, Function<Book, String> function) {
		CellRenderer label = new CellRenderer(theme, function);
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
}
