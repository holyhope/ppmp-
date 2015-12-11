package fr.mlv.school.gui;

import java.awt.Component;
import java.rmi.RemoteException;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import fr.mlv.school.Book;
import fr.mlv.school.Library;

@SuppressWarnings("serial")
public class BuyButtonRenderer extends JButton implements TableCellRenderer {
	private final Library library;
	private final Theme	  theme;

	private BuyButtonRenderer(Theme theme, Library library) {
		this.theme = Objects.requireNonNull(theme);
		this.library = Objects.requireNonNull(library);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (isSelected) {
			theme.applySelectedTo(this);
		} else {
			theme.applyTo(this);
		}

		try {
			Long barCode = Long.parseLong(value.toString());
			Book book = library.searchByBarCode(barCode);
			if (library.isBuyable(book)) {
				setText(Double.toString(book.getCost()));
			} else {
				setText("Pas en vente");
			}
		} catch (NumberFormatException | RemoteException e) {
			setText("Prix inconnu");
			e.printStackTrace(System.err);
		}

		return this;
	}

	public static BuyButtonRenderer construct(Theme theme, Library library) {
		BuyButtonRenderer borrowButtonRenderer = new BuyButtonRenderer(theme, library);
		borrowButtonRenderer.setOpaque(true);
		return borrowButtonRenderer;
	}
}
