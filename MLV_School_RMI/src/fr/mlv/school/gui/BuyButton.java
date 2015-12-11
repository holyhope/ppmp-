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

import fr.mlv.school.User;

@SuppressWarnings("serial")
public class BuyButton extends AbstractCellEditor implements TableCellEditor {
	private final JButton button = new JButton();
	private final User	  user;

	private Long		  value;

	private BuyButton(User user) {
		this.user = user;
	}

	public static BuyButton construct(User user, JCheckBox checkBox) {
		BuyButton buyButton = new BuyButton(user);

		buyButton.button.setOpaque(true);
		buyButton.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buyButton.fireEditingStopped();
			}
		});

		return buyButton;
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