package fr.mlv.school.gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class LabelCellRenderer extends JLabel implements TableCellRenderer {
	private final Theme theme;

	public LabelCellRenderer(Theme theme) {
		this.theme = theme;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (isSelected) {
			theme.applySelectedTo(this);
		} else {
			theme.applyTo(this);
		}

		setText((value == null) ? "" : value.toString());

		return this;
	}
}
