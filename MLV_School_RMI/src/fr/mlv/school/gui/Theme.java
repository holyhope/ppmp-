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

	public void applyTo(JTextField textField) {
		textField.setForeground(primary);
		textField.setBorder(BorderFactory.createLineBorder(primary.darker(), 1, false));
		textField.setBackground(background.brighter());
		textField.setCaretColor(secondary);
		textField.setSelectedTextColor(primary.brighter());
		textField.setSelectionColor(secondary.darker());
		textField.setDisabledTextColor(secondary);
	}

	public void applyTo(JButton button) {
		button.setForeground(primary);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createLineBorder(primary.darker(), 1, true));
		button.setBackground(background.brighter());
	}

	public void applyTo(JLabel label) {
		label.setForeground(primary);
	}

	public void applySelectedTo(JLabel label) {
		label.setForeground(secondary);
	}

	public void applyTo(JTable table) {
		table.setBackground(background);
		table.setGridColor(primary.darker());
		table.setRowHeight(25);
		table.setForeground(primary);
		table.setFocusable(false);
		table.setFillsViewportHeight(true);

		table.getTableHeader().setBackground(background);
		table.getTableHeader().setBorder(BorderFactory.createLineBorder(primary, 1));
	}

	public void applyTo(JScrollPane scrollPane) {
		scrollPane.setBackground(background);
		scrollPane.setOpaque(true);
	}

	public void applySelectedTo(JButton button) {
		button.setForeground(secondary);
		button.setBorder(BorderFactory.createLineBorder(secondary.darker(), 1, true));
		button.setBackground(background.brighter());
	}
}
