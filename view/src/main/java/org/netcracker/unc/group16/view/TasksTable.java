package org.netcracker.unc.group16.view;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;


public class TasksTable extends JTable {

private static final int[][] COLUMNS_WIDTHS = new int[][] {
        {50, 50, 50},
        {65, 65, 65},
        {100, 250, 10000},
        {100, 400, 10000},
        {30, 30, 30}
};

    public TasksTable(TableModel tableModel) {
        super(tableModel);

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setSelectionBackground(new Color(245, 245, 250));
        setSelectionForeground(Color.BLACK);

        getTableHeader().setReorderingAllowed(false);
        for (int i = 0; i < getColumnCount(); i++) {
            TableColumn column = getColumnModel().getColumn(i);
            column.setMinWidth(COLUMNS_WIDTHS[i][0]);
            column.setPreferredWidth(COLUMNS_WIDTHS[i][1]);
            column.setMaxWidth(COLUMNS_WIDTHS[i][2]);
        }

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment( JLabel.CENTER );
        cellRenderer.setBackground(new Color(240, 220, 220));

        getColumnModel().getColumn(4).setCellRenderer(cellRenderer);
    }

}
