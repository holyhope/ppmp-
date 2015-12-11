package fr.mlv.school.gui.table;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import fr.mlv.school.gui.Theme;

@SuppressWarnings("serial")
public class HeaderCellRenderer extends JLabel implements TableCellRenderer {
	private final Theme theme;

	public HeaderCellRenderer(Theme theme) {
		this.theme = theme;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		theme.applyTo(this);

		setText(value == null ? "" : value.toString());
		setFont(new Font(null, Font.BOLD, 16));

		return this;
	}

}
