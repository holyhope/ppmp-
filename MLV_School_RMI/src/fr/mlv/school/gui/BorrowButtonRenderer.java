package fr.mlv.school.gui;

import java.awt.Component;
import java.rmi.RemoteException;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import fr.mlv.school.Library;

@SuppressWarnings("serial")
public class BorrowButtonRenderer extends JButton implements TableCellRenderer {
	private final Library library;
	private final Theme	  theme;

	private BorrowButtonRenderer(Theme theme, Library library) {
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
			if (library.isBookAvailable(library.searchByBarCode(barCode))) {
				setText("Emprunter");
			} else {
				setText("Indisponible");
			}
		} catch (NumberFormatException | RemoteException e) {
			setText("Emprunter");
			e.printStackTrace(System.err);
		}

		return this;
	}

	public static BorrowButtonRenderer construct(Theme theme, Library library) {
		BorrowButtonRenderer borrowButtonRenderer = new BorrowButtonRenderer(theme, library);
		borrowButtonRenderer.setOpaque(true);
		return borrowButtonRenderer;
	}
}
