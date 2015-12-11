package fr.mlv.school.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.EventObject;

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

	private Long		  barCode;

	private BorrowButton(User user, Library library) {
		this.user = user;
		this.library = library;
	}

	public static BorrowButton construct(User user, Library library, JCheckBox checkBox) {
		BorrowButton borrowButton = new BorrowButton(user, library);

		borrowButton.button.setOpaque(true);
		borrowButton.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrowButton.fireEditingStopped();
			}
		});

		return borrowButton;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return false;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		button.setText("Checking...");
		barCode = (Long) value;

		try {
			Book book = library.searchByBarCode(barCode);
			if (library.getBook(book, user)) {
				return button;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
		}

		return button;
	}

	@Override
	public Object getCellEditorValue() {
		return barCode;
	}
}