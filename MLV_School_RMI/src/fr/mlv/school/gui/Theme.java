package fr.mlv.school.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Theme {
	public final Color primary	  = new Color(2, 153, 217);
	public final Color secondary  = new Color(217, 84, 2);
	public final Color background = new Color(40, 40, 40);

	public JTextField applyTo(JTextField textField) {
		textField.setForeground(primary);
		textField.setBorder(BorderFactory.createLineBorder(primary.darker(), 1, false));
		textField.setBackground(background.brighter());
		textField.setCaretColor(secondary);
		textField.setSelectedTextColor(primary.brighter());
		textField.setSelectionColor(secondary.darker());
		textField.setDisabledTextColor(secondary);
		return textField;
	}

	public JButton applyTo(JButton button) {
		button.setForeground(primary);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createLineBorder(primary.darker(), 1, true));
		button.setBackground(background.brighter());
		return button;
	}

	public JLabel applyTo(JLabel label) {
		label.setForeground(primary);
		return label;
	}

	public JLabel applySelectedTo(JLabel label) {
		label.setForeground(secondary);
		return label;
	}

	public JTable applyTo(JTable table) {
		table.setBackground(background);
		table.setGridColor(primary.darker());
		table.setRowHeight(25);
		table.setForeground(primary);
		table.setFocusable(false);
		table.setFillsViewportHeight(true);

		table.getTableHeader().setBackground(background);
		table.getTableHeader().setBorder(BorderFactory.createLineBorder(primary, 1));
		return table;
	}

	public JScrollPane applyTo(JScrollPane scrollPane) {
		scrollPane.setBackground(background);
		scrollPane.setOpaque(true);

		return scrollPane;
	}

	public JButton applySelectedTo(JButton button) {
		button.setForeground(secondary);
		button.setBorder(BorderFactory.createLineBorder(secondary.darker(), 1, true));
		button.setBackground(background.brighter());

		return button;
	}
}
