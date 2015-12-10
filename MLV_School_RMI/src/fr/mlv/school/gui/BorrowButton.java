package fr.mlv.school.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import fr.mlv.school.Book;
import fr.mlv.school.Library;
import fr.mlv.school.User;

@SuppressWarnings("serial")
public class BorrowButton extends AbstractCellEditor implements TableCellEditor {
	private final JButton button = new JButton();
	private final Library library;
	private final User	  user;

	private BorrowButton(User user, Library library) {
		this.user = user;
		this.library = library;
	}

	public static BorrowButton construct(User user, Library library, JCheckBox checkBox) {
		BorrowButton addToKartButton = new BorrowButton(user, library);

		addToKartButton.button.setOpaque(true);
		addToKartButton.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToKartButton.fireEditingStopped();
			}
		});

		return addToKartButton;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		System.out.println("Borrows");

		setEnabled(false);

		try {
			System.out.println(value);
			Book book = library.searchByBarCode((long) value);
			if (library.getBook(book, user)) {
				System.out.println("book borrowed");
				return button;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
		}

		return button;
	}

	public void setEnabled(boolean enabled) {
		button.setEnabled(enabled);
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}
}