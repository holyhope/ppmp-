package fr.mlv.school.gui;

import java.awt.Component;
import java.rmi.RemoteException;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import fr.mlv.school.Library;

@SuppressWarnings("serial")
public class BorrowButtonRenderer extends JButton implements TableCellRenderer {
	private final Library library;

	private BorrowButtonRenderer(Library library) {
		this.library = Objects.requireNonNull(library);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(UIManager.getColor("Button.background"));
		}
		setText((value == null) ? "" : value.toString());

		return this;
	}

	public static BorrowButtonRenderer construct(Library library) {
		BorrowButtonRenderer borrowButtonRenderer = new BorrowButtonRenderer(library);
		borrowButtonRenderer.setOpaque(true);
		return borrowButtonRenderer;
	}

	@Override
	public String getText() {
		Long barCode;
		try {
			barCode = Long.parseLong(super.getText());
		} catch (NumberFormatException e) {
			return "Chargement...";
		}

		try {
			return library.isBookAvailable(library.searchByBarCode(barCode)) ? "Emprunter" : "Non disponible";
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
		}
		return "Emprunter";
	}
}
