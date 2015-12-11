package fr.mlv.school.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import fr.mlv.school.Library;
import fr.mlv.school.User;

@SuppressWarnings("serial")
public class BuyButton extends AbstractCellEditor implements TableCellEditor {
	private final JButton button = new JButton();
	private final Library library;
	private final User	  user;

	private Long		  value;

	private BuyButton(User user, Library library) {
		this.user = user;
		this.library = library;
	}

	public static BuyButton construct(User user, Library library, JCheckBox checkBox) {
		BuyButton borrowButton = new BuyButton(user, library);

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
		this.value = (Long) value;

		// TODO Buy book
		System.out.println("todo: buy book");

		return button;
	}

	public void setEnabled(boolean enabled) {
		button.setEnabled(enabled);
	}

	@Override
	public Object getCellEditorValue() {
		return value;
	}
}